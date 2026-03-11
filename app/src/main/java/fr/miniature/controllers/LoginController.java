package fr.miniature.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="Login", urlPatterns={"/inscription", "/connexion"})
public class LoginController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if(path.equals("/inscription")){
            req.getRequestDispatcher("/inscription.jsp").forward(req, resp);
        }else{
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
            Error error = new Error("données valide.");
            req.setAttribute("error", error);
            req.getRequestDispatcher("/inscription.jsp").forward(req, resp);

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
            Error error = new Error("données valide.");
            req.setAttribute("error", error);
            req.getRequestDispatcher("/connexion.jsp").forward(req, resp);

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
}
