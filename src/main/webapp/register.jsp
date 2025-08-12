<%-- 
    Document   : register
    Bootstrap Modernization: Fatmanur & Habibe, 2025 
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f2f5fa; }
        .register-card {
            max-width: 400px;
            margin: 60px auto;
            border-radius: 18px;
            box-shadow: 0 2px 16px #0001;
        }
        .login-link { float: right; font-size: 0.98em; }
    </style>
</head>
<body>
    <div class="register-card card p-4">
        <h2 class="mb-3 text-center text-success">User Registration</h2>
        <form action="RegisterServlet" method="post">
            <div class="mb-3">
                <label class="form-label">Username:</label>
                <input type="text" name="username" class="form-control" required autocomplete="username">
            </div>
            <div class="mb-3">
                <label class="form-label">Password:</label>
                <input type="password" name="password" class="form-control" required autocomplete="new-password">
            </div>
            <div class="mb-3">
                <label class="form-label">Role:</label>
                <select name="role" class="form-select">
                    <option value="receptionist">Receptionist</option>
                    <option value="admin">Admin</option>
                </select>
            </div>
            <div class="d-flex justify-content-between align-items-center">
                <button type="submit" class="btn btn-success">Register</button>
                <a href="login.jsp" class="login-link">Login</a>
            </div>
        </form>
        <%
            String error = (String)request.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger mt-3 text-center"><%= error %></div>
        <%
            }
            String msg = (String)request.getAttribute("msg");
            if (msg != null) {
        %>
            <div class="alert alert-success mt-3 text-center"><%= msg %></div>
        <%
            }
        %>
    </div>
</body>
</html>
