package com.habibe.hotelautomationmaven.model.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.habibe.hotelautomationmaven.model.DBConnection;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                // ROL bilgisini de session'a kaydet!
                String role = rs.getString("role");
                session.setAttribute("role", role);

                // Giriş başarılı ise ana sayfaya yönlendir (ör: dashboard.jsp)
                response.sendRedirect("dashboard.jsp");
            } else {
                // Giriş başarısız
                request.setAttribute("error", "Kullanıcı adı veya şifre yanlış!");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Bir hata oluştu: " + e.getMessage());
        }
    }
}
