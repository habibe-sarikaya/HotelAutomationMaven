package com.habibe.hotelautomationmaven.model.controller;

import com.habibe.hotelautomationmaven.model.Reservation;
import com.habibe.hotelautomationmaven.model.ReservationDAO;
import com.habibe.hotelautomationmaven.model.CustomerDAO;
import com.habibe.hotelautomationmaven.model.RoomDAO;
import com.habibe.hotelautomationmaven.model.Customer;
import com.habibe.hotelautomationmaven.model.Room;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.text.SimpleDateFormat;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ReservationDAO.deleteReservation(id);
            response.sendRedirect("ReservationServlet");
            return;
        }
        if ("updateStatus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");

            // CHECK-IN'e özel çakışma kontrolü!
            if ("checked_in".equals(status)) {
                if (!ReservationDAO.canCheckIn(id)) {
                    session.setAttribute("error", "Bu odaya bu tarihlerde zaten giriş yapılmış başka bir rezervasyon var!");
                    response.sendRedirect("ReservationServlet");
                    return;
                }
            }

            ReservationDAO.updateReservationStatus(id, status);
            response.sendRedirect("ReservationServlet");
            return;
        }
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            List<Reservation> reservations = ReservationDAO.getAllReservations();
            Reservation editR = null;
            for (Reservation r : reservations) {
                if (r.getId() == id) {
                    editR = r;
                    break;
                }
            }
            if (editR != null) {
                request.setAttribute("editReservation", editR);
                request.setAttribute("customers", CustomerDAO.getAllCustomers());
                request.setAttribute("rooms", RoomDAO.getAllRooms());
                RequestDispatcher rd = request.getRequestDispatcher("editReservation.jsp");
                rd.forward(request, response);
                return;
            }
            response.sendRedirect("ReservationServlet");
            return;
        }

        // Hata mesajı gösterimi (session’dan)
        String error = (String) session.getAttribute("error");
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }

        List<Reservation> reservations = ReservationDAO.getAllReservations();
        List<Customer> customers = CustomerDAO.getAllCustomers();
        List<Room> rooms = RoomDAO.getAllRooms();

        if (reservations == null) reservations = new java.util.ArrayList<>();
        if (customers == null) customers = new java.util.ArrayList<>();
        if (rooms == null) rooms = new java.util.ArrayList<>();

        request.setAttribute("reservations", reservations);
        request.setAttribute("customers", customers);
        request.setAttribute("rooms", rooms);

        RequestDispatcher rd = request.getRequestDispatcher("reservations.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int customerId = Integer.parseInt(request.getParameter("customer_id"));
                int roomId = Integer.parseInt(request.getParameter("room_id"));
                String checkinStr = request.getParameter("checkin");
                String checkoutStr = request.getParameter("checkout");
                String status = request.getParameter("status");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date checkin = sdf.parse(checkinStr);
                java.util.Date checkout = sdf.parse(checkoutStr);

                // Oda uygun mu? (kendi kaydı hariç!)
                if (!ReservationDAO.isRoomAvailableForEdit(roomId, checkin, checkout, id)) {
                    session.setAttribute("error", "Seçilen odada bu tarihler arasında başka bir rezervasyon var!");
                    response.sendRedirect("ReservationServlet");
                    return;
                }

                Reservation r = new Reservation();
                r.setId(id);
                r.setCustomerId(customerId);
                r.setRoomId(roomId);
                r.setCheckin(checkin);
                r.setCheckout(checkout);
                r.setStatus(status);

                ReservationDAO.updateReservation(r);
                session.removeAttribute("editReservation");
                response.sendRedirect("ReservationServlet");
                return;
            }

            // Yeni rezervasyon ekleme (action yoksa veya add ise)
            int customerId = Integer.parseInt(request.getParameter("customer_id"));
            int roomId = Integer.parseInt(request.getParameter("room_id"));
            String checkinStr = request.getParameter("checkin");
            String checkoutStr = request.getParameter("checkout");
            String status = request.getParameter("status");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date checkin = sdf.parse(checkinStr);
            java.util.Date checkout = sdf.parse(checkoutStr);

            // Oda uygun mu? (yeni rezervasyon için)
            if (!ReservationDAO.isRoomAvailable(roomId, checkin, checkout)) {
                session.setAttribute("error", "Bu odaya seçtiğiniz tarihlerde zaten bir rezervasyon var!");
                response.sendRedirect("ReservationServlet");
                return;
            }

            Reservation r = new Reservation();
            r.setCustomerId(customerId);
            r.setRoomId(roomId);
            r.setCheckin(checkin);
            r.setCheckout(checkout);
            r.setStatus(status);

            ReservationDAO.addReservation(r);
            response.sendRedirect("ReservationServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Hata: " + e.getMessage());
        }
    }
}
