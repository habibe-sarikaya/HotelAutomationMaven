<%-- 
    Document   : reservations
    Bootstrap Modernization: Fatmanur & Habibe, 2025 
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Reservation" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Customer" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Room" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation Management</title>
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
    List<Reservation> reservations = (List<Reservation>)request.getAttribute("reservations");
    List<Customer> customers = (List<Customer>)request.getAttribute("customers");
    List<Room> rooms = (List<Room>)request.getAttribute("rooms");
    if (reservations == null || customers == null || rooms == null) {
        response.sendRedirect("ReservationServlet");
        return;
    }
%>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Reservations</h2>
        <a href="dashboard.jsp" class="btn btn-secondary btn-sm">Home</a>
    </div>

    <%-- Show error message as Bootstrap alert if present --%>
    <%
       String error = (String)request.getAttribute("error");
       if (error != null) { %>
       <div class="alert alert-danger" role="alert"><%= error %></div>
    <% } %>

    <table class="table table-bordered table-striped shadow-sm bg-white">
        <thead class="table-warning">
            <tr>
                <th>Customer</th>
                <th>Room</th>
                <th>Check-in</th>
                <th>Check-out</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <%
            for(Reservation r : reservations) {
                // Find customer
                String customerName = "";
                for(Customer c : customers) {
                    if(c.getId() == r.getCustomerId()) {
                        customerName = c.getName() + " " + c.getSurname();
                        break;
                    }
                }
                // Find room
                String roomNumber = "";
                for(Room room : rooms) {
                    if(room.getId() == r.getRoomId()) {
                        roomNumber = room.getRoomNumber();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= customerName %></td>
            <td><%= roomNumber %></td>
            <td><%= r.getCheckin() %></td>
            <td><%= r.getCheckout() %></td>
            <td><span class="badge bg-<%= "reserved".equals(r.getStatus()) ? "secondary" : ("checked_in".equals(r.getStatus()) ? "success" : ("checked_out".equals(r.getStatus()) ? "primary" : "danger")) %>">
                <%= r.getStatus().replace('_',' ') %></span></td>
            <td>
                <a href="ReservationServlet?action=delete&id=<%= r.getId() %>" 
                   class="btn btn-outline-danger btn-sm"
                   onclick="return confirm('Delete this reservation?')">Delete</a>
                <a href="ReservationServlet?action=edit&id=<%= r.getId() %>" class="btn btn-sm btn-warning">Edit</a>

                <% if ("reserved".equals(r.getStatus())) { %>
                    <a href="ReservationServlet?action=updateStatus&id=<%= r.getId() %>&status=checked_in"
                       class="btn btn-outline-success btn-sm ms-1">Check-in</a>
                <% } %>
                <% if ("checked_in".equals(r.getStatus())) { %>
                    <a href="ReservationServlet?action=updateStatus&id=<%= r.getId() %>&status=checked_out"
                       class="btn btn-outline-primary btn-sm ms-1">Check-out</a>
                <% } %>
                <% if (!"cancelled".equals(r.getStatus())) { %>
                    <a href="ReservationServlet?action=updateStatus&id=<%= r.getId() %>&status=cancelled"
                       class="btn btn-outline-secondary btn-sm ms-1">Cancel</a>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <% if ("admin".equals(role) || "receptionist".equals(role)) { %>
    <div class="card mt-5 shadow-sm" style="max-width: 600px;">
        <div class="card-body">
            <h5 class="card-title mb-3">Add New Reservation</h5>
            <form action="ReservationServlet" method="post">
                <div class="mb-3">
                    <label class="form-label">Customer:</label>
                    <select name="customer_id" class="form-select" required>
                        <%
                            for(Customer c : customers) {
                        %>
                        <option value="<%= c.getId() %>"><%= c.getName() %> <%= c.getSurname() %></option>
                        <% } %>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Room:</label>
                    <select name="room_id" class="form-select" required>
                        <%
                            for(Room r : rooms) {
                        %>
                        <option value="<%= r.getId() %>"><%= r.getRoomNumber() %> - <%= r.getType() %></option>
                        <% } %>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Check-in Date:</label>
                    <input type="date" name="checkin" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Check-out Date:</label>
                    <input type="date" name="checkout" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Status:</label>
                    <select name="status" class="form-select">
                        <option value="reserved">Reserved</option>
                        <option value="checked_in">Checked In</option>
                        <option value="cancelled">Cancelled</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-warning">Add Reservation</button>
            </form>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>
