package fr.miniature.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import fr.miniature.models.Post;
import fr.miniature.models.Posts;
import fr.miniature.models.User;
import fr.miniature.models.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/feed")
public class FeedController extends HttpServlet {
    private Users users = Users.getInstance();
    private Posts posts = Posts.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            resp.sendRedirect("/connexion");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        User userSession = users.getUserByID(userID);
        ArrayList<Post> postsList = posts.getPosts();
        Map<String, User> listOfUser = new HashMap<>();
        for (Post post : postsList) {
            String userId = post.getUserID();
            User user = users.getUserByID(userID);
            listOfUser.put(userId, user);
        }

        req.setAttribute("user", userSession);
        req.setAttribute("posts", postsList);
        req.setAttribute("users", listOfUser);

        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userID") == null) {
            resp.sendRedirect("/connexion");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        User userSession = users.getUserByID(userID);
        if (userSession == null) {
            resp.sendRedirect("/connexion");
            return;
        }

        ArrayList<Post> newPostsList = null;
        Error error = null;

        String action = req.getParameter("action");
        if (action == null || !actionIsValid(action)) {
            error = new Error("action invalide");
        } else {
            if (action.equals("creerpost")) {
                String postContent = req.getParameter("newPost");

                if (postContent == null || postContent.isEmpty() || postContent.isBlank()) {
                    error = new Error("post invalide.");
                    newPostsList = posts.getPosts();
                } else {
                    Post newPost = new Post(userID, postContent);
                    newPostsList = posts.addPost(newPost);
                }
            }else{
                String postID = req.getParameter("postID");
                Post currentPost = posts.getPost(postID);
                currentPost.islike();
                newPostsList = posts.getPosts();
            }
        }

        

        Map<String, User> listOfUser = new HashMap<>();
        for (Post post : newPostsList) {
            String userId = post.getUserID();
            User user = users.getUserByID(userID);
            listOfUser.put(userId, user);
        }

        req.setAttribute("user", userSession);
        req.setAttribute("posts", newPostsList);
        req.setAttribute("users", listOfUser);
        req.setAttribute("error", error);
        req.getRequestDispatcher("/feed.jsp").forward(req, resp);

    }

    private boolean actionIsValid(String action) {
        return (action.equals("liker") || action.equals("creerpost"));
    }

}
