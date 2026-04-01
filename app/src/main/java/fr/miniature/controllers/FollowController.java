package fr.miniature.controllers;

import java.io.IOException;

import fr.miniature.models.User;
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

@WebServlet("/follow")
public class FollowController extends HttpServlet {
    
    private UserRepository users = UserRepository.getInstance();
    private Session session = Session.getInstance();
    private DataValidator dataValidator = DataValidator.getInstance();



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            session.setRequest(req).checkSession();
            String authorID = req.getParameter("authorID");
            dataValidator.check(req, "authorID");
            User userSession = session.getUserSession();            
            User author = users.findByID(authorID);
            userSession.addAbonnement(author); 
            
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
