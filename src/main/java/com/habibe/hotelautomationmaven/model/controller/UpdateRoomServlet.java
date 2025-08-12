package com.habibe.hotelautomationmaven.model.controller;

import com.habibe.hotelautomationmaven.model.Room;
import com.habibe.hotelautomationmaven.model.RoomDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/UpdateRoomServlet")
public class UpdateRoomServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String type = request.getParameter("type");
            String status = request.getParameter("status");
            double price = Double.parseDouble(request.getParameter("price"));

            Room editRoom = (Room) session.getAttribute("editRoom");
            Room room = new Room();
            room.setId(id);
            room.setRoomNumber(editRoom != null ? editRoom.getRoomNumber() : ""); // Güvenlik için, oda numarası değişmemeli!
            room.setType(type);
            room.setStatus(status);
            room.setPrice(price);

            RoomDAO.updateRoom(room);

            session.removeAttribute("editRoom");
            session.setAttribute("msg", "Oda başarıyla güncellendi.");
            response.sendRedirect("RoomServlet");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Güncelleme sırasında hata oluştu: " + e.getMessage());
            response.sendRedirect("RoomServlet");
        }
    }
}
