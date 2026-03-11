package fr.miniature.controllers;

import java.io.IOException;

import fr.miniature.models.User;
import fr.miniature.models.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="Login", urlPatterns={"/inscription", "/connexion"})
public class LoginController extends HttpServlet {

    Users users = Users.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if(path.equals("/inscription")){
            req.getRequestDispatcher("/inscription.jsp").forward(req, resp);
        }else{
            HttpSession session = req.getSession(false);
            if(session != null && session.getAttribute("userID") != null ){                
                resp.sendRedirect("/feed");
            }
            

            req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if(path.equals("/inscription")){
            inscription(req, resp);
        }else{
            connexion(req, resp);
        }
    }

    private void inscription(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String pseudo = req.getParameter("pseudo");
        String password = req.getParameter("password");

        if(
            isValidInput(firstname) &&
            isValidInput(lastname) &&
            isValidInput(pseudo) &&
            isValidInput(password)
        ){
            // créer un user
            // session
            User user = new User(firstname, lastname, pseudo, password);
            users.addNewUser(user);
            addSession(req, user);
            resp.sendRedirect("/feed");
            

        }else{
            Error error = new Error("données invalides.");
            req.setAttribute("error", error);
            req.getRequestDispatcher("/inscription.jsp").forward(req, resp);
        }
    }

    private void connexion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        String pseudo = req.getParameter("pseudo");
        String password = req.getParameter("password");

        if(
            isValidInput(pseudo) &&
            isValidInput(password)
        ){
            // créer un user
            User user = users.getUserForConnexion(pseudo, password);

            if(user == null){
                Error error = new Error("utilisateur inconnu.");
                req.setAttribute("error", error);
                req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
               
            }
            addSession(req, user);
            resp.sendRedirect("/feed");


        }else{
            Error error = new Error("données invalides.");
            req.setAttribute("error", error);
            req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
        }
    }


    private boolean isValidInput(String value){
        boolean isValid = (value != null && !value.isBlank() && !value.isEmpty());
        System.out.println(value+" : "+ isValid);
        return isValid;
    }

    private void addSession(HttpServletRequest req, User user){
        String key = "userID";
        String value = Integer.toString(user.getId());
        HttpSession session = req.getSession(false);
        session.setAttribute(key, value);
    }
}
