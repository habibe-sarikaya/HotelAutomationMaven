<%-- 
    Document   : rooms
    Modernized by Fatmanur & Habibe, 2025
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Room" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Room Management</title>
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
    List<Room> rooms = (List<Room>)request.getAttribute("rooms");
    if (rooms == null) {
        response.sendRedirect("RoomServlet");
        return;
    }
    String error = (String) request.getAttribute("error");
    String msg = (String) request.getAttribute("msg");
%>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Room List</h2>
        <a href="dashboard.jsp" class="btn btn-secondary btn-sm">Home</a>
    </div>
    <% if (error != null) { %>
        <div class="alert alert-danger"><%= error %></div>
    <% } else if (msg != null) { %>
        <div class="alert alert-success"><%= msg %></div>
    <% } %>
    <table class="table table-bordered table-striped shadow-sm bg-white">
        <thead class="table-primary">
            <tr>
                <th>Number</th>
                <th>Type</th>
                <th>Status</th>
                <th>Price</th>
                <% if ("admin".equals(role)) { %>
                <th>Actions</th>
                <% } %>
            </tr>
        </thead>
        <tbody>
        <% for(Room r : rooms) { %>
            <tr>
                <form action="RoomServlet" method="post">
                    <td>
                        <input type="hidden" name="id" value="<%= r.getId() %>"/>
                        <input type="text" name="roomNumber" value="<%= r.getRoomNumber() %>" class="form-control-plaintext" readonly>
                    </td>
                    <td>
                        <input type="text" name="type" value="<%= r.getType() %>" class="form-control-plaintext" readonly>
                    </td>
                    <td>
                        <input type="text" name="status" value="<%= r.getStatus() %>" class="form-control-plaintext" readonly>
                    </td>
                    <td>
                        <input type="number" name="price" step="0.01" value="<%= r.getPrice() %>" class="form-control-plaintext" readonly>
                    </td>
                    <% if ("admin".equals(role)) { %>
                    <td>
                        <button name="action" value="edit" class="btn btn-warning btn-sm">Edit</button>
                        <button name="action" value="delete" class="btn btn-danger btn-sm" onclick="return confirm('Delete this room?')">Delete</button>
                    </td>
                    <% } %>
                </form>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% if ("admin".equals(role)) { %>
    <div class="card mt-5 shadow-sm" style="max-width: 500px;">
        <div class="card-body">
            <h5 class="card-title mb-3">Add New Room</h5>
            <form id="add-room-form" action="RoomServlet" method="post">
                <input type="hidden" name="action" value="add">
                <div class="mb-3">
                    <label class="form-label">Number:</label>
                    <input type="text" name="roomNumber" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Type:</label>
                    <input type="text" name="type" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Status:</label>
                    <input type="text" name="status" class="form-control" required value="empty">
                </div>
                <div class="mb-3">
                    <label class="form-label">Price:</label>
                    <input type="number" name="price" step="0.01" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary">Add Room</button>
            </form>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>
