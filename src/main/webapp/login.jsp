<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Automation Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f2f5fa; }
        .login-card {
            max-width: 370px;
            margin: 60px auto;
            border-radius: 18px;
            box-shadow: 0 2px 16px #0001;
        }
        .register-link { float: right; font-size: 0.98em; }
    </style>
</head>
<body>
    <div class="login-card card p-4">
        <h2 class="mb-3 text-center text-primary">User Login</h2>
        <form action="LoginServlet" method="post">
            <div class="mb-3">
                <label class="form-label">Username:</label>
                <input type="text" name="username" class="form-control" required autocomplete="username">
            </div>
            <div class="mb-3">
                <label class="form-label">Password:</label>
                <input type="password" name="password" class="form-control" required autocomplete="current-password">
            </div>
            <div class="d-flex justify-content-between align-items-center">
                <button type="submit" class="btn btn-primary">Login</button>
                <a href="register.jsp" class="register-link">Register</a>
            </div>
        </form>
        <%
            String error = (String)request.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger mt-3 text-center"><%= error %></div>
        <%
            }
        %>
    </div>
</body>
</html>
