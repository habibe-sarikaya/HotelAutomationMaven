package com.habibe.hotelautomationmaven.model.controller;

import com.habibe.hotelautomationmaven.model.Customer;
import com.habibe.hotelautomationmaven.model.CustomerDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        // Silme işlemi (POST ile yapılması daha güvenli ama burada GET de destekleniyor)
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                CustomerDAO.deleteCustomer(id);
                session.setAttribute("msg", "Müşteri başarıyla silindi.");
            } catch (Exception e) {
                session.setAttribute("error", "Müşteri silinirken hata oluştu: " + e.getMessage());
            }
            response.sendRedirect("CustomerServlet");
            return;
        }
        // Düzenleme formu için
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Customer customer = CustomerDAO.getCustomerById(id);
            session.setAttribute("editCustomer", customer);
            response.sendRedirect("editCustomer.jsp");
            return;
        }

        List<Customer> customers = CustomerDAO.getAllCustomers();
        if (customers == null) customers = new java.util.ArrayList<>();

        String error = (String) session.getAttribute("error");
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            session.removeAttribute("msg");
        }

        request.setAttribute("customers", customers);
        RequestDispatcher rd = request.getRequestDispatcher("customers.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                CustomerDAO.deleteCustomer(id);
                session.setAttribute("msg", "Müşteri başarıyla silindi.");
                response.sendRedirect("CustomerServlet");
                return;
            }
            if ("edit".equals(action)) {
                // Güncelleme işlemi (editCustomer.jsp üzerinden gelirse)
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");

                Customer c = new Customer();
                c.setId(id);
                c.setName(name);
                c.setSurname(surname);
                c.setPhone(phone);
                c.setEmail(email);

                CustomerDAO.updateCustomer(c);
                session.setAttribute("msg", "Müşteri başarıyla güncellendi.");
                response.sendRedirect("CustomerServlet");
                return;
            }
            if ("add".equals(action) || action == null) {
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");

                Customer c = new Customer();
                c.setName(name);
                c.setSurname(surname);
                c.setPhone(phone);
                c.setEmail(email);

                boolean added = CustomerDAO.addCustomer(c);
                if (added) {
                    session.setAttribute("msg", "Müşteri başarıyla eklendi.");
                } else {
                    session.setAttribute("error", "Bu müşteri zaten mevcut!");
                }
                response.sendRedirect("CustomerServlet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Müşteri işleminde hata oluştu: " + e.getMessage());
            response.sendRedirect("CustomerServlet");
        }
    }
}
