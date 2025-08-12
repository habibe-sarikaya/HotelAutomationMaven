<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Customer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Customer editCustomer = (Customer) session.getAttribute("editCustomer");
    if (editCustomer == null) {
        response.sendRedirect("CustomerServlet");
        return;
    }
%>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Edit Customer</h2>
        <a href="CustomerServlet" class="btn btn-secondary btn-sm">Back to Customer List</a>
    </div>

    <form action="CustomerServlet" method="post" class="card shadow-sm p-4" style="max-width: 500px;">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" value="<%= editCustomer.getId() %>">
        <div class="mb-3">
            <label class="form-label">Name:</label>
            <input type="text" name="name" class="form-control" value="<%= editCustomer.getName() %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Surname:</label>
            <input type="text" name="surname" class="form-control" value="<%= editCustomer.getSurname() %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Phone:</label>
            <input type="text" name="phone" class="form-control" value="<%= editCustomer.getPhone() %>">
        </div>
        <div class="mb-3">
            <label class="form-label">Email:</label>
            <input type="email" name="email" class="form-control" value="<%= editCustomer.getEmail() %>">
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
</div>
</body>
</html>
