package fr.miniature.controllers;

import java.io.IOException;

import domain.models.postForClient.PostForClient;

import java.util.Arrays;
import java.util.List;
import fr.miniature.models.Post;
import fr.miniature.models.User;
import infrastructure.database.PostRepository;
import infrastructure.errors.errorWithRedirection.ErrorWithRedirection;
import infrastructure.errors.invalideData.InvalideData;
import infrastructure.facade.PostsFacade;
import infrastructure.session.Session;
import infrastructure.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feed")
public class FeedController extends HttpServlet {
    private PostRepository posts = PostRepository.getInstance();
    private Session session = Session.getInstance();
    private DataValidator dataValidator = DataValidator.getInstance();
    private PostsFacade postsFacade = new PostsFacade();
    private List<String> actionPossible = Arrays.asList("liker", "creerpost", "follow");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            session
                .setRequest(req)
                .checkSession();

            User userSession = session.getUserSession();

            String abonnement = req.getParameter("abonnement");
            if(abonnement == null){
                abonnement = "all";
            }
            // si la session existe               
            List<PostForClient> posts = postsFacade.getPosts(abonnement);       
        
            req.setAttribute("user", userSession);
            req.setAttribute("posts", posts); 
            req.getRequestDispatcher("/feed.jsp").forward(req, resp);
        }catch(ErrorWithRedirection error){
            resp.sendRedirect(error.getPath());
            return;
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{

            session.setRequest(req).checkSession();
            
            String action = req.getParameter("action");
            dataValidator.check(req, "action");
            checkIfActionIsValid(action);

            if (action.equals("creerpost")) {
                savePost(req);
            }else if(action.equals("liker")){
                likePost(req);
            }else{
                getFollowingPosts(req, resp);
            }
            

        }catch(ErrorWithRedirection error){
            resp.sendRedirect(error.getPath());
            return;
        }catch(InvalideData error){
            req.setAttribute("error", error);
        }catch(Error error){            
            req.setAttribute("error", error);
        }finally{
            User userSession = session.getUserSession();
            List<PostForClient> newPostsList = postsFacade.getPosts("all");

            req.setAttribute("user", userSession);            
            req.setAttribute("posts", newPostsList);
            req.getRequestDispatcher("/feed.jsp").forward(req, resp);
        }

    }

    private void savePost(HttpServletRequest req){
        User userSession = session.getUserSession();
        String userID = userSession.getID();
        String postContent = req.getParameter("newPost");
        dataValidator.check(req, "newPost");           
       
        Post newPost = new Post(userID, postContent);
        posts.save(newPost);
        
    }

    private void likePost(HttpServletRequest req){
        User userSession = session.getUserSession();
        String userID = userSession.getID();
        String postID = req.getParameter("postID");
        dataValidator.check(req, "postID");    
        Post currentPost = posts.findByID(postID);
        currentPost.isLike(userID);
    }

    private void getFollowingPosts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User userSession = session.getUserSession();
        String filter = req.getParameter("filter");
        dataValidator.check(req, "filter");    
        List<PostForClient> posts = postsFacade.getPosts(filter);       
       
        req.setAttribute("user", userSession);
        req.setAttribute("posts", posts); 
        req.getRequestDispatcher("/feed.jsp").forward(req, resp);

    }

    private void checkIfActionIsValid(String action) {
        boolean isValid = actionPossible.contains(action);
        if(!isValid){
            throw new InvalideData("l'action n'est pas valide");
        }
    }

}
