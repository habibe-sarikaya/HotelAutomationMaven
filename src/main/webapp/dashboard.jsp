<%-- 
    Document   : dashboard
    Bootstrap Modernization: Fatmanur & Habibe, 2025 
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Automation Dashboard</title>
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
%>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Hotel Automation</h2>
        <a href="logout.jsp" class="btn btn-outline-danger btn-sm">Log Out</a>
    </div>

    <div class="alert alert-info mb-4">
        <strong>Welcome, <%= username %>!</strong>
        <br>
        <%
            if ("admin".equals(role)) {
        %>
            <span>You are in the admin panel. You have all system permissions.</span>
        <%
            } else if ("receptionist".equals(role)) {
        %>
            <span>You are in the receptionist panel. You only have access to reservation and customer operations.</span>
        <%
            }
        %>
    </div>

    <div class="row g-4 mb-3">
        <div class="col-md-4">
            <a href="RoomServlet" class="text-decoration-none">
                <div class="card text-center shadow h-100 border-0">
                    <div class="card-body">
                        <h5 class="card-title text-primary mb-2">Room Management</h5>
                        <p class="card-text small">View the room list and add new rooms</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-md-4">
            <a href="CustomerServlet" class="text-decoration-none">
                <div class="card text-center shadow h-100 border-0">
                    <div class="card-body">
                        <h5 class="card-title text-success mb-2">Customer Management</h5>
                        <p class="card-text small">View customers and add new customers</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-md-4">
            <a href="ReservationServlet" class="text-decoration-none">
                <div class="card text-center shadow h-100 border-0">
                    <div class="card-body">
                        <h5 class="card-title text-warning mb-2">Reservation Management</h5>
                        <p class="card-text small">Reservation operations and updates</p>
                    </div>
                </div>
            </a>
        </div>
    </div>
</div>
</body>
</html>
