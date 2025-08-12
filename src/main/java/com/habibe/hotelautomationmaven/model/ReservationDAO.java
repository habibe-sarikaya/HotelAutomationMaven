package com.habibe.hotelautomationmaven.model;

import java.sql.*;
import java.util.*;

public class ReservationDAO {

    public static List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM reservations")) {
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setCustomerId(rs.getInt("customer_id"));
                r.setRoomId(rs.getInt("room_id"));
                r.setCheckin(rs.getDate("checkin"));
                r.setCheckout(rs.getDate("checkout"));
                r.setStatus(rs.getString("status"));
                reservations.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // Çakışma kontrolü (yeni rezervasyon ekleme)
    public static boolean isRoomAvailable(int roomId, java.util.Date checkin, java.util.Date checkout) {
        boolean available = true;
        try (Connection conn = DBConnection.getConnection()) {
            String sql =
                "SELECT 1 FROM reservations " +
                "WHERE room_id = ? AND status != 'cancelled' " +
                "AND ( (checkin < ? AND checkout > ?) )";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setDate(2, new java.sql.Date(checkout.getTime()));
            ps.setDate(3, new java.sql.Date(checkin.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) available = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return available;
    }

    // Yeni eklenen: Check-in yapılabilir mi?
    public static boolean canCheckIn(int reservationId) {
        try (Connection conn = DBConnection.getConnection()) {
            // Bu rezervasyonu bul
            String sql1 = "SELECT room_id, checkin, checkout FROM reservations WHERE id=?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, reservationId);
            ResultSet rs1 = ps1.executeQuery();
            if (!rs1.next()) return false;

            int roomId = rs1.getInt("room_id");
            java.sql.Date checkin = rs1.getDate("checkin");
            java.sql.Date checkout = rs1.getDate("checkout");


            // Aynı oda ve tarih için başka aktif rezervasyon var mı?
            String sql2 =
                "SELECT 1 FROM reservations " +
                "WHERE room_id = ? AND id != ? AND status IN ('reserved','checked_in') " +
                "AND (checkin < ? AND checkout > ?)";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, roomId);
            ps2.setInt(2, reservationId);
            ps2.setDate(3, checkout);
            ps2.setDate(4, checkin);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) return false; // Çakışma var
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addReservation(Reservation r) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO reservations (customer_id, room_id, checkin, checkout, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getCustomerId());
            ps.setInt(2, r.getRoomId());
            ps.setDate(3, new java.sql.Date(r.getCheckin().getTime()));
            ps.setDate(4, new java.sql.Date(r.getCheckout().getTime()));
            ps.setString(5, r.getStatus()); // <<--- Buraya dikkat!
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteReservation(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM reservations WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Edit işlemi için çakışma kontrolü
    public static boolean isRoomAvailableForEdit(int roomId, java.util.Date checkin, java.util.Date checkout, int reservationId) {
        boolean available = true;
        try (Connection conn = DBConnection.getConnection()) {
            String sql =
                "SELECT 1 FROM reservations " +
                "WHERE room_id = ? AND id != ? AND status != 'cancelled' " +
                "AND ( (checkin < ? AND checkout > ?) )";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setInt(2, reservationId);
            ps.setDate(3, new java.sql.Date(checkout.getTime()));
            ps.setDate(4, new java.sql.Date(checkin.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) available = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return available;
    }

    public static void updateReservation(Reservation r) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE reservations SET customer_id=?, room_id=?, checkin=?, checkout=?, status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getCustomerId());
            ps.setInt(2, r.getRoomId());
            ps.setDate(3, new java.sql.Date(r.getCheckin().getTime()));
            ps.setDate(4, new java.sql.Date(r.getCheckout().getTime()));
            ps.setString(5, r.getStatus());
            ps.setInt(6, r.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateReservationStatus(int id, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE reservations SET status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
