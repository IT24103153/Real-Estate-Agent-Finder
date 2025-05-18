<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.Property" %>
<%@ page import="com.example.entity.User" %>
<%
    Property property = (Property) request.getAttribute("property");
    User user = (User) session.getAttribute("user");
%>
<html>
<head>
    <title>Property Details - Real Estate Agent Finder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Property Details</h4>
            </div>
            <div class="card-body">
                <h5><%= property.getTitle() %></h5>
                <p><strong>Description:</strong> <%= property.getDescription() %></p>
                <p><strong>Price:</strong> $<%= property.getPrice() %></p>
                <p><strong>Status:</strong> <%= property.getStatus() %></p>
                <% if (property.getImageUrl() != null && !property.getImageUrl().isEmpty()) { %>
                    <img src="<%= property.getImageUrl() %>" alt="Property Image" style="width: 300px; height: 200px; object-fit: cover;">
                <% } %>
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/properties" class="btn btn-secondary">Back to List</a>
                    <% if (user.getId().equals(property.getOwnerId())) { %>
                        <a href="${pageContext.request.contextPath}/properties?action=edit&id=<%= property.getId() %>" class="btn btn-warning">Edit</a>
                        <form action="${pageContext.request.contextPath}/properties" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= property.getId() %>">
                            <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this property?')">Delete</button>
                        </form>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 