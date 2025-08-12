package com.habibe.hotelautomationmaven.model.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import com.habibe.hotelautomationmaven.model.DBConnection;

@WebServlet(name = "TestDBServlet", urlPatterns = {"/TestDBServlet"})
public class TestDBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                out.println("<h2>Veritabanı bağlantısı başarılı!</h2>");
            } else {
                out.println("<h2>Bağlantı başarısız!</h2>");
            }
        }
    }
}
