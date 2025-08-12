<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Room" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Room</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
    Room editRoom = (Room) session.getAttribute("editRoom");
    if (editRoom == null) {
        response.sendRedirect("RoomServlet");
        return;
    }
%>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Update Room</h2>
        <a href="RoomServlet" class="btn btn-secondary btn-sm">Back to Rooms</a>
    </div>
    <form action="UpdateRoomServlet" method="post" class="card shadow-sm p-4" style="max-width: 500px;">
        <input type="hidden" name="id" value="<%= editRoom.getId() %>">
        <div class="mb-3">
            <label class="form-label">Number:</label>
            <input type="text" class="form-control" value="<%= editRoom.getRoomNumber() %>" readonly>
        </div>
        <div class="mb-3">
            <label class="form-label">Type:</label>
            <input type="text" name="type" class="form-control" value="<%= editRoom.getType() %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Status:</label>
            <input type="text" name="status" class="form-control" value="<%= editRoom.getStatus() %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Price:</label>
            <input type="number" name="price" step="0.01" class="form-control" value="<%= editRoom.getPrice() %>" required>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
</div>
</body>
</html>
