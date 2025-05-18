<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Real Estate Agent Finder - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/agents">Agent Finder</a>
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/agents">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/agents?action=search">Search Agents</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/agents?action=edit">Add Agent</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1>Real Estate Agents</h1>
        <div class="mb-3">
            <a href="${pageContext.request.contextPath}/agents?action=sort" class="btn btn-primary">Sort by Rating</a>
        </div>
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
                    <th>Actions</th>
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
                        <td>
                            <a href="${pageContext.request.contextPath}/agents?action=edit&id=${agent.id}" class="btn btn-sm btn-warning">Edit</a>
                            <a href="${pageContext.request.contextPath}/agents?action=delete&id=${agent.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this agent?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>