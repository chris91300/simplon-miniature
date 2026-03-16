package fr.miniature.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import fr.miniature.models.Comment;
import fr.miniature.models.Comments;
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
    private Comments comments = Comments.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            resp.sendRedirect("/connexion");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        User userSession = users.getUserByID(userID);
        ArrayList<Post> postsList = new ArrayList<Post>();;

        String abonnement = req.getParameter("abonnement");

        if(abonnement != null){
            if(abonnement.equals("all")){
                ArrayList<User> abonnements = userSession.getAbonnements();
                for(User author: abonnements){
                    ArrayList<Post> authorPosts = posts.getPostFrom(author.getId());
                    for(Post post: authorPosts){
                        Collections.addAll(postsList, post);
                    }                
                    
                }
                
            }else{
                String authorID = abonnement;
                ArrayList<Post> authorPosts = posts.getPostFrom(authorID);
                    for(Post post: authorPosts){
                        Collections.addAll(postsList, post);
                    }  
            }
            
        }else{
             postsList = posts.getPosts();
        }

       


        Map<String, User> listOfUser = new HashMap<>();
        for (Post post : postsList) {
            String userId = post.getUserID();
            User user = users.getUserByID(userID);
            listOfUser.put(userId, user);
        }

        
       Map<String, ArrayList<Comment>> listOfCommentsByPostID = new HashMap<>();
        for (Post post : postsList) {
            String postID = post.getID();
            if(!listOfCommentsByPostID.containsKey(postID)){
                ArrayList<Comment> commentList = comments.getCommmentsFor(postID);
                listOfCommentsByPostID.put(postID, commentList);
            }
            
        }
        System.out.println("la liste size= "+listOfCommentsByPostID.size());
        req.setAttribute("user", userSession);
        req.setAttribute("posts", postsList);
        req.setAttribute("users", listOfUser);
        req.setAttribute("listOfCommentsByPostID", listOfCommentsByPostID);
       

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

        
       Map<String, ArrayList<Comment>> listOfCommentsByPostID = new HashMap<>();
        for (Post post : newPostsList) {
            String postID = post.getID();
            if(!listOfCommentsByPostID.containsKey(postID)){
                ArrayList<Comment> commentList = comments.getCommmentsFor(postID);
                listOfCommentsByPostID.put(postID, commentList);
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
        req.setAttribute("listOfCommentsByPostID", listOfCommentsByPostID);
        req.setAttribute("error", error);
        req.getRequestDispatcher("/feed.jsp").forward(req, resp);

    }

    private boolean actionIsValid(String action) {
        return (action.equals("liker") || action.equals("creerpost"));
    }

}
