package com.example;    

import com.example.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/agents")
public class AgentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        // If user is not an agent and trying to access edit page, redirect to list page
        if ("edit".equals(action) && !"AGENT".equals(user.getUserType())) {
            request.setAttribute("error", "Only agents can access this page");
            action = "list";
        }

        switch (action) {
            case "search":
                request.getRequestDispatcher("/WEB-INF/views/search.jsp").forward(request, response);
                break;
            case "edit":
                request.getRequestDispatcher("/WEB-INF/views/manage.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}