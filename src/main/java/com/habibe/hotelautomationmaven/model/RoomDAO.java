package com.habibe.hotelautomationmaven.model;

import java.sql.*;
import java.util.*;

public class RoomDAO {

    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM rooms")) {
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setType(rs.getString("type"));
                room.setStatus(rs.getString("status"));
                room.setPrice(rs.getDouble("price"));
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static boolean roomExists(String roomNumber) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM rooms WHERE room_number=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roomNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addRoom(Room room) throws Exception {
    if (roomExists(room.getRoomNumber())) {
        throw new Exception("Aynı oda numarası zaten mevcut!");
    }
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO rooms (room_number, type, status, price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, room.getRoomNumber());
        ps.setString(2, room.getType());
        ps.setString(3, room.getStatus());
        ps.setDouble(4, room.getPrice());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            room.setId(rs.getInt(1)); // 🔥 burada ID'yi nesneye kaydediyoruz
        }
    }
}


    public static void deleteRoom(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM rooms WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Room getRoomById(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM rooms WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setType(rs.getString("type"));
                room.setStatus(rs.getString("status"));
                room.setPrice(rs.getDouble("price"));
                return room;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateRoom(Room room) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE rooms SET type=?, status=?, price=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, room.getType());
            ps.setString(2, room.getStatus());
            ps.setDouble(3, room.getPrice());
            ps.setInt(4, room.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
