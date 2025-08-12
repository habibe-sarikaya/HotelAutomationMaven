package com.habibe.hotelautomationmaven.model.controller;

import com.habibe.hotelautomationmaven.model.Room;
import com.habibe.hotelautomationmaven.model.RoomDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Room> rooms = RoomDAO.getAllRooms();
        if (rooms == null) rooms = new java.util.ArrayList<>();

        HttpSession session = request.getSession();
        String error = (String) session.getAttribute("error");
        String msg = (String) session.getAttribute("msg");
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        if (msg != null) {
            request.setAttribute("msg", msg);
            session.removeAttribute("msg");
        }

        request.setAttribute("rooms", rooms);
        RequestDispatcher rd = request.getRequestDispatcher("rooms.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                RoomDAO.deleteRoom(id);
                session.setAttribute("msg", "Oda başarıyla silindi.");
                response.sendRedirect("RoomServlet");
                return;
            }
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Room room = RoomDAO.getRoomById(id);
                if (room != null) {
                    session.setAttribute("editRoom", room);
                    response.sendRedirect("editRoom.jsp");
                    return;
                }
            }
            // DİKKAT: Buradaki "update" action'ını KALDIRDIK!
            if ("add".equals(action) || action == null) {
                String roomNumber = request.getParameter("roomNumber");
                String type = request.getParameter("type");
                String status = request.getParameter("status");
                double price = Double.parseDouble(request.getParameter("price"));

                Room room = new Room();
                room.setRoomNumber(roomNumber);
                room.setType(type);
                room.setStatus(status);
                room.setPrice(price);

                RoomDAO.addRoom(room);
                session.setAttribute("msg", "Oda başarıyla eklendi.");
                response.sendRedirect("RoomServlet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "İşlem sırasında hata oluştu: " + e.getMessage());
            response.sendRedirect("RoomServlet");
        }
    }
}
