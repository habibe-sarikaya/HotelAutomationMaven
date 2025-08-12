<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.habibe.hotelautomationmaven.model.Reservation,com.habibe.hotelautomationmaven.model.Customer,com.habibe.hotelautomationmaven.model.Room,java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Reservation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null || (!"admin".equals(role) && !"receptionist".equals(role))) {
        response.sendRedirect("login.jsp");
        return;
    }
    Reservation r = (Reservation) request.getAttribute("editReservation");
    if (r == null) {
        response.sendRedirect("ReservationServlet");
        return;
    }
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
    List<Room> rooms = (List<Room>) request.getAttribute("rooms");
%>
<div class="container py-5">
    <div class="card mx-auto shadow-sm" style="max-width: 600px;">
        <div class="card-body">
            <h5 class="card-title mb-4">Edit Reservation</h5>
            <form action="ReservationServlet" method="post">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="id" value="<%= r.getId() %>">
                <div class="mb-3">
                    <label class="form-label">Customer:</label>
                    <select name="customer_id" class="form-control" required>
                        <% for (Customer c : customers) { %>
                            <option value="<%= c.getId() %>" <%= (c.getId() == r.getCustomerId() ? "selected" : "") %>>
                                <%= c.getName() %> <%= c.getSurname() %>
                            </option>
                        <% } %>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Room:</label>
                    <select name="room_id" class="form-control" required>
                        <% for (Room room : rooms) { %>
                            <option value="<%= room.getId() %>" <%= (room.getId() == r.getRoomId() ? "selected" : "") %>>
                                <%= room.getRoomNumber() %> - <%= room.getType() %>
                            </option>
                        <% } %>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Check-in Date:</label>
                    <input type="date" name="checkin" class="form-control" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(r.getCheckin()) %>" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Check-out Date:</label>
                    <input type="date" name="checkout" class="form-control" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(r.getCheckout()) %>" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Status:</label>
                    <select name="status" class="form-control">
                        <option value="reserved" <%= "reserved".equals(r.getStatus()) ? "selected" : "" %>>Reserved</option>
                        <option value="checked_in" <%= "checked_in".equals(r.getStatus()) ? "selected" : "" %>>Checked In</option>
                        <option value="checked_out" <%= "checked_out".equals(r.getStatus()) ? "selected" : "" %>>Checked Out</option>
                        <option value="cancelled" <%= "cancelled".equals(r.getStatus()) ? "selected" : "" %>>Cancelled</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-warning">Update</button>
                <a href="ReservationServlet" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
