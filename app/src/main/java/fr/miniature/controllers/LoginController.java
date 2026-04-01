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

@WebServlet(name="Login", urlPatterns={"/inscription", "/connexion", "/login"})
public class LoginController extends HttpServlet {

    private Session session = Session.getInstance();
    private UserRepository users = UserRepository.getInstance();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try{
             String path = req.getServletPath();
            if(path.equals("/inscription")){// si c'est une demande d'inscription
                // on envoie la page d'inscription
                req.getRequestDispatcher("/inscription.jsp").forward(req, resp);
            }else{ 
                session.setRequest(req).checkSessionAndRedirectIfExist();
                
                // sinon on retourne la page de connexion
                req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
            }
       }catch(ErrorWithRedirection error){
            resp.sendRedirect(error.getPath());
            return;
       }
       
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        try{
            if(path.equals("/inscription")){// demande d'inscription
                inscription(req, resp);
            }else if(path.equals("/connexion")){// demande de connexion
                connexion(req, resp);
            }else{
                session.deleteSession();
            }
        }catch(InvalideData error){
            System.out.println(error.getMessage());
            System.out.println(path);
            req.setAttribute("error", error);
            req.getRequestDispatcher(path+".jsp").forward(req, resp);
        }catch(ErrorWithRedirection error){
            resp.sendRedirect(error.getPath());
            return;
        }catch(Error error){
            System.out.println(error.getMessage());
            System.out.println(path);
            req.setAttribute("error", error);
            req.getRequestDispatcher(path+".jsp").forward(req, resp);       
           
        }
        
               
    }
  
    private void inscription(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        // on récupère les données d'inscription
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String pseudo = req.getParameter("pseudo");
        String password = req.getParameter("password");

        dataValidator.check(req, "firstname", "lastname", "pseudo", "password");       
        
        // si les données sont valide            
        User user = new User(firstname, lastname, pseudo, password);
        users.save(user);
        session.createSession(req, user.getID());
        resp.sendRedirect("/feed");
        return;
       
      
    }

    private void connexion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        String pseudo = req.getParameter("pseudo");
        String password = req.getParameter("password");

        dataValidator.check(req, "pseudo", "password");
        
        // créer un user
        User user = users.findByNameAndPassword(pseudo, password);            
        session.createSession(req, user.getID());
        resp.sendRedirect("/feed");
        return;

    }
}
