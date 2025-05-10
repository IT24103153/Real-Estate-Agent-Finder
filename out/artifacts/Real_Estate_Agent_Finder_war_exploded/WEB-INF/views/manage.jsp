<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Real Estate Agent Finder - Manage Agent</title>
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/agents?action=edit">Add Agent</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1>${agent != null ? 'Edit Agent' : 'Add Agent'}</h1>
        <form action="${pageContext.request.contextPath}/agents" method="post" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="${agent != null ? 'update' : 'create'}">
            <input type="hidden" name="id" value="${agent != null ? agent.id : ''}">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${agent != null ? agent.name : ''}" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${agent != null ? agent.email : ''}" required>
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone</label>
                <input type="text" class="form-control" id="phone" name="phone" value="${agent != null ? agent.phone : ''}" required>
            </div>
            <div class="mb-3">
                <label for="location" class="form-label">Location</label>
                <input type="text" class="form-control" id="location" name="location" value="${agent != null ? agent.location : ''}" required>
            </div>
            <div class="mb-3">
                <label for="experience" class="form-label">Experience</label>
                <input type="text" class="form-control" id="experience" name="experience" value="${agent != null ? agent.experience : ''}" required>
            </div>
            <div class="mb-3">
                <label for="specialties" class="form-label">Specialties</label>
                <input type="text" class="form-control" id="specialties" name="specialties" value="${agent != null ? agent.specialties : ''}" required>
            </div>
            <div class="mb-3">
                <label for="rating" class="form-label">Rating (0-5)</label>
                <input type="number" step="0.1" min="0" max="5" class="form-control" id="rating" name="rating" value="${agent != null ? agent.rating : '0'}" required>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="${pageContext.request.contextPath}/agents" class="btn btn-secondary">Cancel</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>