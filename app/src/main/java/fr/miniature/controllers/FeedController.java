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

@WebServlet("/feed")
public class FeedController extends HttpServlet {
    private Users users = Users.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            resp.sendRedirect("/connexion");

        }

        String userID = (String) session.getAttribute("userID");
        User user = users.getUserByID(userID);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }

}
