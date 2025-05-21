<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Real Estate Agent Finder - Search</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/agents">Agent Finder</a>
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/agents">Home</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/agents?action=search">Search Agents</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/agents?action=edit">Add Agent</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1>Search Agents</h1>
        <form action="${pageContext.request.contextPath}/agents" method="get" class="mb-3">
            <input type="hidden" name="action" value="search">
            <div class="row">
                <div class="col-md-6">
                    <label for="location" class="form-label">Location</label>
                    <input type="text" class="form-control" id="location" name="location" value="${location}">
                </div>
                <div class="col-md-6">
                    <label for="specialties" class="form-label">Specialties</label>
                    <input type="text" class="form-control" id="specialties" name="specialties" value="${specialties}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Search</button>
        </form>

        <h2>Results</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Location</th>
                    <th>Experience</th>
                    <th>Specialties</th>
                    <th>Rating</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="agent" items="${agents}">
                    <tr>
                        <td>${agent.id}</td>
                        <td>${agent.name}</td>
                        <td>${agent.email}</td>
                        <td>${agent.phone}</td>
                        <td>${agent.location}</td>
                        <td>${agent.experience}</td>
                        <td>${agent.specialties}</td>
                        <td>${agent.rating}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>