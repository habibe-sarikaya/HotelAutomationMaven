<%-- 
    Document   : customers
    Bootstrap Modernization: Fatmanur & Habibe, 2025
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Customer" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Customer> customers = (List<Customer>)request.getAttribute("customers");
    if (customers == null) {
        response.sendRedirect("CustomerServlet");
        return;
    }
    String error = (String) request.getAttribute("error");
    String msg = (String) request.getAttribute("msg");
%>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Customer List</h2>
        <a href="dashboard.jsp" class="btn btn-secondary btn-sm">Home</a>
    </div>
    
    <% if (error != null) { %>
        <div class="alert alert-danger"><%= error %></div>
    <% } else if (msg != null) { %>
        <div class="alert alert-success"><%= msg %></div>
    <% } %>
    
    <table class="table table-bordered table-striped shadow-sm bg-white">
        <thead class="table-success">
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Phone</th>
                <th>Email</th>
                <% if ("admin".equals(role) || "receptionist".equals(role)) { %>
                <th>Actions</th>
                <% } %>
            </tr>
        </thead>
        <tbody>
        <% for(Customer c : customers) { %>
            <tr>
                <td>
                    <input type="hidden" name="id" value="<%= c.getId() %>"/>
                    <input type="text" name="name" value="<%= c.getName() %>" class="form-control-plaintext" readonly>
                </td>
                <td>
                    <input type="text" name="surname" value="<%= c.getSurname() %>" class="form-control-plaintext" readonly>
                </td>
                <td>
                    <input type="text" name="phone" value="<%= c.getPhone() %>" class="form-control-plaintext" readonly>
                </td>
                <td>
                    <input type="email" name="email" value="<%= c.getEmail() %>" class="form-control-plaintext" readonly>
                </td>
                <% if ("admin".equals(role) || "receptionist".equals(role)) { %>
                <td>
                    <a href="CustomerServlet?action=edit&id=<%= c.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                    <form action="CustomerServlet" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= c.getId() %>"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Delete this customer?')">Delete</button>
                    </form>
                </td>
                <% } %>
            </tr>
        <% } %>
        </tbody>
    </table>

    <% if ("admin".equals(role) || "receptionist".equals(role)) { %>
    <div class="card mt-5 shadow-sm" style="max-width: 500px;">
        <div class="card-body">
            <h5 class="card-title mb-3">Add New Customer</h5>
            <form action="CustomerServlet" method="post">
                <input type="hidden" name="action" value="add">
                <div class="mb-3">
                    <label class="form-label">Name:</label>
                    <input type="text" name="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Surname:</label>
                    <input type="text" name="surname" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone:</label>
                    <input type="text" name="phone" class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" name="email" class="form-control">
                </div>
                <button type="submit" class="btn btn-success">Add Customer</button>
            </form>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>
