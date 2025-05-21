<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.User" %>
<% User user = (User) session.getAttribute("user"); %>
<html>
<head>
    <title>My Profile - Real Estate Agent Finder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">My Profile</h4>
                        <div>
                            <a href="${pageContext.request.contextPath}/agents" class="btn btn-outline-light btn-sm me-2">Home</a>
                            <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-outline-light btn-sm">Logout</a>
                        </div>
                    </div>
                    <div class="card-body">
                        <% if(request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= request.getAttribute("error") %>
                        </div>
                        <% } %>
                        <% if(request.getAttribute("success") != null) { %>
                        <div class="alert alert-success" role="alert">
                            <%= request.getAttribute("success") %>
                        </div>
                        <% } %>
                        
                        <div class="mb-4">
                            <div class="d-flex align-items-center mb-3">
                                <div class="bg-primary text-white rounded-circle p-3 me-3" style="width: 60px; height: 60px; display: flex; align-items: center; justify-content: center; font-size: 24px;">
                                    <%= user.getName().substring(0, 1).toUpperCase() %>
                                </div>
                                <div>
                                    <h5 class="mb-0"><%= user.getName() %></h5>
                                    <span class="badge <%= user.getUserType().equals("BUYER") ? "bg-info" : (user.getUserType().equals("SELLER") ? "bg-warning" : "bg-success") %>">
                                        <%= user.getUserType() %>
                                    </span>
                                </div>
                            </div>
                        </div>
                        
                        <form action="${pageContext.request.contextPath}/auth/update-profile" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" value="<%= user.getUsername() %>" readonly>
                                <div class="form-text">Username cannot be changed</div>
                            </div>
                            <div class="mb-3">
                                <label for="name" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="name" name="name" value="<%= user.getName() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" value="<%= user.getEmail() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="phone" class="form-label">Phone</label>
                                <input type="text" class="form-control" id="phone" name="phone" value="<%= user.getPhone() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">New Password</label>
                                <input type="password" class="form-control" id="password" name="password">
                                <div class="form-text">Leave blank to keep your current password</div>
                            </div>
                            <div class="d-flex justify-content-between">
                                <button type="submit" class="btn btn-primary">Update Profile</button>
                                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteAccountModal">
                                    Delete Account
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Delete Account Modal -->
    <div class="modal fade" id="deleteAccountModal" tabindex="-1" aria-labelledby="deleteAccountModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title" id="deleteAccountModalLabel">Confirm Account Deletion</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete your account? This action cannot be undone.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <form action="${pageContext.request.contextPath}/auth/delete-account" method="post">
                        <button type="submit" class="btn btn-danger">Delete My Account</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 