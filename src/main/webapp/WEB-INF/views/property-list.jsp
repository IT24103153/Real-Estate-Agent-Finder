<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.Property" %>
<%@ page import="com.example.entity.User" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%
    java.util.List<Property> properties = (java.util.List<Property>) request.getAttribute("properties");
    User user = (User) session.getAttribute("user");
    String homeUrl = "#";
    String ctx = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
    if (user != null) {
        if ("AGENT".equals(user.getUserType())) {
            homeUrl = ctx + "/agents";
        } else if ("SELLER".equals(user.getUserType())) {
            homeUrl = ctx + "/auth/seller-home";
        } else if ("BUYER".equals(user.getUserType())) {
            homeUrl = ctx + "/auth/buyer-home";
        }
    }
%>
<html>
<head>
    <title>Property Listings - Real Estate Agent Finder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= homeUrl %>">Home</a>
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/properties">Properties</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2>Property Listings</h2>
            <% if (!"BUYER".equals(user.getUserType())) { %>
                <a href="${pageContext.request.contextPath}/properties?action=create" class="btn btn-primary">Add Property</a>
            <% } %>
        </div>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Property property : properties) { %>
                <tr>
                    <td><%= property.getTitle() %></td>
                    <td><%= property.getDescription() %></td>
                    <td>$<%= property.getPrice() %></td>
                    <td><%= property.getStatus() %></td>
                    <td>
                        <% if (property.getImageUrl() != null && !property.getImageUrl().isEmpty()) { %>
                            <img src="<%= property.getImageUrl() %>" alt="Property Image" style="width: 80px; height: 60px; object-fit: cover;">
                        <% } %>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/properties?action=details&id=<%= property.getId() %>" class="btn btn-sm btn-info">Details</a>
                        <% if (user.getId().equals(property.getOwnerId())) { %>
                            <a href="${pageContext.request.contextPath}/properties?action=edit&id=<%= property.getId() %>" class="btn btn-sm btn-warning">Edit</a>
                            <form action="${pageContext.request.contextPath}/properties" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= property.getId() %>">
                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this property?')">Delete</button>
                            </form>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 