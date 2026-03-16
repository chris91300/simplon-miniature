package fr.miniature.controllers;

import java.io.IOException;

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

@WebServlet("/comments")
public class CommentsController extends HttpServlet {

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

        String postUserID = req.getParameter("postUserID");
        String postID = req.getParameter("postID");

        if(!isValidParam(postID) && !isValidParam(postUserID)){
            resp.sendRedirect("/index.html");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        User userSession = users.getUserByID(userID);
        Post post = posts.getPost(postID);
        User author = users.getUserByID(post.getUserID());

        if(userSession == null || post == null || author == null){
            resp.sendRedirect("/index.html");
            return;
        }

        req.setAttribute("user", userSession);
        req.setAttribute("post",post );
        req.setAttribute("author",author );
        
        req.getRequestDispatcher("/comments.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userID") == null) {
            resp.sendRedirect("/connexion");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        String action = req.getParameter("action");
        String postID = null;
        if(action.equals("commenter")){
            postID = req.getParameter("postID");
            //Post post = posts.getPost(postID);
            String commentContent = req.getParameter("commentContent");
            Comment newComment = new Comment(userID, commentContent, postID);
           // post.addComment(newComment);
            comments.addComment(newComment);

            resp.sendRedirect("/feed");
            return;
        }else{
            System.out.println("on ne veut pas commenter");
            User userSession = users.getUserByID(userID);
            if(action.equals("s'abonner")){
                System.out.println("on veut s'abonner");
                String authorID = req.getParameter("authorID");
                User author = users.getUserByID(authorID);
                userSession.addAbonnement(author);
            }
            
            Post post = posts.getPost(postID);
            User author = users.getUserByID(userID);

            if(userSession == null || post == null || author == null){
                resp.sendRedirect("/feed");
                return;
            }

            /*req.setAttribute("user", userSession);
            req.setAttribute("post",post );
            req.setAttribute("author",author );
            
            req.getRequestDispatcher("/comments.jsp").forward(req, resp);*/
        }
       
        
      
    }

    private boolean isValidParam(String value){
        boolean isValid = (value != null && !value.isBlank() && !value.isEmpty());
        System.out.println(value+" : "+ isValid);
        return isValid;
    }
}
