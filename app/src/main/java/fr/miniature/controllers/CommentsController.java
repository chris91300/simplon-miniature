package fr.miniature.controllers;

import java.io.IOException;

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

@WebServlet("/comments")
public class CommentsController extends HttpServlet {

    private Users users = Users.getInstance();
    private Posts posts = Posts.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            resp.sendRedirect("/connexion");
            return;
        }

        String postUserID = req.getParameter("postUserID");
        String postID = req.getParameter("postID");

        if(!isValidParam(postID) && !isValidParam(postUserID)){
            resp.sendRedirect("/index.html");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        User userSession = users.getUserByID(userID);
        Post post = posts.getPost(postID);
        User author = users.getUserByID(userID);

        if(userSession == null || post == null || author == null){
            resp.sendRedirect("/index.html");
            return;
        }

        req.setAttribute("user", userSession);
        req.setAttribute("post",post );
        req.setAttribute("author",author );
        
        req.getRequestDispatcher("/comments.jsp").forward(req, resp);
    }


    private boolean isValidParam(String value){
        boolean isValid = (value != null && !value.isBlank() && !value.isEmpty());
        System.out.println(value+" : "+ isValid);
        return isValid;
    }
}
