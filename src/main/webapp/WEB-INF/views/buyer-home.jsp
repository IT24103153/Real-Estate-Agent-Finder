<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.User" %>
<% User user = (User) session.getAttribute("user"); %>
<html>
<head>
    <title>Buyer Home - Real Estate Agent Finder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Buyer Dashboard</a>
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/profile">My Profile</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/agents?action=search">Search Agents</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/properties">Properties</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container mt-4">
        <h2>Welcome, <%= user.getName() %>!</h2>
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card mb-3">
                    <div class="card-header bg-info text-white">Recommended Properties</div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">2BHK Apartment in Downtown - $250,000</li>
                        <li class="list-group-item">3BHK Villa in Suburbs - $400,000</li>
                        <li class="list-group-item">Studio Flat near Park - $120,000</li>
                    </ul>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card mb-3">
                    <div class="card-header bg-success text-white">Saved Agents</div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Mark Agent - mark.agent@example.com</li>
                        <li class="list-group-item">Jane Seller - jane.seller@example.com</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 