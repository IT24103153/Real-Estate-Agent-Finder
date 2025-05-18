<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.User" %>
<% User user = (User) session.getAttribute("user"); %>
<html>
<head>
    <title>Seller Home - Real Estate Agent Finder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Seller Dashboard</a>
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/profile">My Profile</a>
                <a class="nav-link" href="#">My Listings</a>
                <a class="nav-link" href="#">Add Property</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container mt-4">
        <h2>Welcome, <%= user.getName() %>!</h2>
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card mb-3">
                    <div class="card-header bg-warning text-dark">My Listings</div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Luxury Condo in City Center - $500,000</li>
                        <li class="list-group-item">Family House in Suburbs - $350,000</li>
                    </ul>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card mb-3">
                    <div class="card-header bg-primary text-white">Recent Inquiries</div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">John Buyer - Interested in Condo</li>
                        <li class="list-group-item">Alice Buyer - Requested a visit for Family House</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 