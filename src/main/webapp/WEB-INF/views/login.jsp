<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - Real Estate Agent Finder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">Login</h4>
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
                        <% if(request.getParameter("deleted") != null) { %>
                        <div class="alert alert-info" role="alert">
                            Your account has been successfully deleted.
                        </div>
                        <% } %>
                        <form action="${pageContext.request.contextPath}/auth/login" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Login</button>
                            </div>
                        </form>
                        <p class="mt-3 text-center">
                            Don't have an account? <a href="${pageContext.request.contextPath}/auth/register">Register here</a>
                        </p>
                        <div class="mt-4">
                            <p class="text-center fw-bold">Demo Accounts (username/password)</p>
                            <div class="d-flex justify-content-around">
                                <div class="text-center">
                                    <span class="badge bg-info">Buyer</span>
                                    <p class="mb-0">buyer1 / password</p>
                                </div>
                                <div class="text-center">
                                    <span class="badge bg-warning">Seller</span>
                                    <p class="mb-0">seller1 / password</p>
                                </div>
                                <div class="text-center">
                                    <span class="badge bg-success">Agent</span>
                                    <p class="mb-0">agent1 / password</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 