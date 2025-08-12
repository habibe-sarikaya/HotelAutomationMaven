package com.habibe.hotelautomationmaven.model;

import java.sql.*;
import java.util.*;

public class CustomerDAO {

    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM customers")) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setSurname(rs.getString("surname"));
                c.setPhone(rs.getString("phone"));
                c.setEmail(rs.getString("email"));
                customers.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Aynı müşteri var mı? (email veya telefon ile)
    public static boolean isCustomerExists(String phone, String email) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id FROM customers WHERE phone = ? OR email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addCustomer(Customer c) {
        if (c == null) { // null müşteri kontrolü
            System.out.println("addCustomer: Parametre olarak null müşteri geldi!");
            return false;
        }
        if (isCustomerExists(c.getPhone(), c.getEmail())) {
            // Aynı müşteri var!
            return false;
        }
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO customers (name, surname, phone, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getSurname());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getEmail());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteCustomer(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM customers WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Customer getCustomerById(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM customers WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setSurname(rs.getString("surname"));
                c.setPhone(rs.getString("phone"));
                c.setEmail(rs.getString("email"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateCustomer(Customer c) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE customers SET name=?, surname=?, phone=?, email=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getSurname());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getEmail());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
