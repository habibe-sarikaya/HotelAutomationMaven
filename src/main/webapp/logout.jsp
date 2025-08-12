<%-- 
    Document   : logout
    Created on : 3 Haz 2025, 01:58:14
    Author     : Habibe
--%>

<%@ page language="java" %>
<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>
