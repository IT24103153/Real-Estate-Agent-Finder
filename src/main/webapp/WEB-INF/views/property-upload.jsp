<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.Property" %>
<%
    Property property = (Property) request.getAttribute("property");
    boolean isEdit = property != null;
%>
<html>
<head>
    <title><%= isEdit ? "Edit Property" : "Add Property" %> - Real Estate Agent Finder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0"><%= isEdit ? "Edit Property" : "Add Property" %></h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/properties" method="post">
                            <% if (isEdit) { %>
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="<%= property.getId() %>">
                            <% } else { %>
                                <input type="hidden" name="action" value="create">
                            <% } %>
                            <div class="mb-3">
                                <label for="title" class="form-label">Title</label>
                                <input type="text" class="form-control" id="title" name="title" value="<%= isEdit ? property.getTitle() : "" %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" required><%= isEdit ? property.getDescription() : "" %></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="price" class="form-label">Price</label>
                                <input type="number" step="0.01" class="form-control" id="price" name="price" value="<%= isEdit ? property.getPrice() : "" %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status" required>
                                    <option value="AVAILABLE" <%= isEdit && "AVAILABLE".equals(property.getStatus()) ? "selected" : "" %>>Available</option>
                                    <option value="SOLD" <%= isEdit && "SOLD".equals(property.getStatus()) ? "selected" : "" %>>Sold</option>
                                    <option value="UNAVAILABLE" <%= isEdit && "UNAVAILABLE".equals(property.getStatus()) ? "selected" : "" %>>Unavailable</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="imageUrl" class="form-label">Image URL</label>
                                <input type="text" class="form-control" id="imageUrl" name="imageUrl" value="<%= isEdit ? property.getImageUrl() : "" %>">
                            </div>
                            <button type="submit" class="btn btn-primary"><%= isEdit ? "Update Property" : "Add Property" %></button>
                            <a href="${pageContext.request.contextPath}/properties" class="btn btn-secondary">Cancel</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 