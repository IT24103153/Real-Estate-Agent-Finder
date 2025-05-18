package com.example;

import com.example.entity.Property;
import com.example.entity.User;
import com.example.util.PropertyService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/properties")
public class PropertyServlet extends HttpServlet {
    private final PropertyService propertyService = PropertyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                request.getRequestDispatcher("/WEB-INF/views/property-upload.jsp").forward(request, response);
                break;
            case "edit":
                String id = request.getParameter("id");
                Optional<Property> property = propertyService.getPropertyById(id);
                property.ifPresent(p -> request.setAttribute("property", p));
                request.getRequestDispatcher("/WEB-INF/views/property-upload.jsp").forward(request, response);
                break;
            case "details":
                id = request.getParameter("id");
                property = propertyService.getPropertyById(id);
                property.ifPresent(p -> request.setAttribute("property", p));
                request.getRequestDispatcher("/WEB-INF/views/property-details.jsp").forward(request, response);
                break;
            default:
                List<Property> properties = propertyService.getAllProperties();
                request.setAttribute("properties", properties);
                request.getRequestDispatcher("/WEB-INF/views/property-list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        if (action == null) action = "create";

        switch (action) {
            case "create":
                Property property = new Property();
                property.setTitle(request.getParameter("title"));
                property.setDescription(request.getParameter("description"));
                property.setPrice(Double.parseDouble(request.getParameter("price")));
                property.setStatus(request.getParameter("status"));
                property.setOwnerId(user.getId());
                property.setImageUrl(request.getParameter("imageUrl"));
                propertyService.createProperty(property);
                response.sendRedirect(request.getContextPath() + "/properties");
                break;
            case "update":
                String id = request.getParameter("id");
                Optional<Property> opt = propertyService.getPropertyById(id);
                if (opt.isPresent()) {
                    property = opt.get();
                    property.setTitle(request.getParameter("title"));
                    property.setDescription(request.getParameter("description"));
                    property.setPrice(Double.parseDouble(request.getParameter("price")));
                    property.setStatus(request.getParameter("status"));
                    property.setImageUrl(request.getParameter("imageUrl"));
                    propertyService.updateProperty(property);
                }
                response.sendRedirect(request.getContextPath() + "/properties");
                break;
            case "delete":
                id = request.getParameter("id");
                propertyService.deleteProperty(id);
                response.sendRedirect(request.getContextPath() + "/properties");
                break;
        }
    }
} 