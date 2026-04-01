package fr.miniature.controllers;

import java.io.IOException;

import fr.miniature.models.Comment;
import fr.miniature.models.Post;
import fr.miniature.models.User;
import infrastructure.database.CommentRepository;
import infrastructure.database.PostRepository;
import infrastructure.database.UserRepository;
import infrastructure.errors.errorWithRedirection.ErrorWithRedirection;
import infrastructure.errors.invalideData.InvalideData;
import infrastructure.session.Session;
import infrastructure.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class CommentsController extends HttpServlet {

    private UserRepository users = UserRepository.getInstance();
    private PostRepository posts = PostRepository.getInstance();
    private CommentRepository comments = CommentRepository.getInstance();
    private Session session = Session.getInstance();
    private DataValidator dataValidator = DataValidator.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            session.setRequest(req).checkSession();            
            dataValidator.check(req, "postID");
            String postID = req.getParameter("postID");
            
            User userSession = session.getUserSession();
            Post post = posts.findByID(postID);
            User author = users.findByID(post.getUserID());

            if(post == null || author == null){
                resp.sendRedirect("/index.html");
                return;
            }

            req.setAttribute("user", userSession);
            req.setAttribute("post",post );
            req.setAttribute("author",author );        
            req.getRequestDispatcher("/comments.jsp").forward(req, resp);

        }catch(ErrorWithRedirection error){
            resp.sendRedirect(error.getPath());
            return;
        }
       
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            session.setRequest(req).checkSession();            
            dataValidator.check(req, "postID", "commentContent");

            User userSession = session.getUserSession();
            String postID = req.getParameter("postID"); 
            String commentContent = req.getParameter("commentContent");
            
            Comment newComment = new Comment(userSession.getID(), commentContent, postID);
            comments.save(newComment);

            resp.sendRedirect("/feed");
            return;       

        }catch(InvalideData error){
            req.setAttribute("error", error);
             resp.sendRedirect("/feed");
            return;   
        }catch(ErrorWithRedirection error){
            resp.sendRedirect(error.getPath());
            return;
        }
        
      
    }
}
