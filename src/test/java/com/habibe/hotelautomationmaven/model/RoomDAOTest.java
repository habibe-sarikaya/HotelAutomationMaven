package com.habibe.hotelautomationmaven.model;

import com.habibe.hotelautomationmaven.model.Room;
import com.habibe.hotelautomationmaven.model.RoomDAO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class RoomDAOTest {

    // Her test için eşsiz bir oda numarası üretelim:
    private Room createTestRoom() {
        Room room = new Room();
        int uniq = (int) (System.currentTimeMillis() % 100000);
        room.setRoomNumber("JUnit" + uniq);
        room.setType("Deluxe");
        room.setStatus("empty");
        room.setPrice(999.0);
        return room;
    }

    @Test
    public void testAddRoom() throws Exception {
        Room room = createTestRoom();
        assertDoesNotThrow(() -> RoomDAO.addRoom(room), "Oda eklenemedi!");
        assertTrue(RoomDAO.roomExists(room.getRoomNumber()), "Eklenen oda bulunamadı!");
    }

    @Test
    public void testRoomExists() throws Exception {
        Room room = createTestRoom();
        RoomDAO.addRoom(room);
        assertTrue(RoomDAO.roomExists(room.getRoomNumber()), "Oda bulunamıyor!");
    }

    @Test
    public void testGetAllRooms() {
        List<Room> rooms = RoomDAO.getAllRooms();
        assertNotNull(rooms, "Oda listesi null geldi!");
        // Oda yoksa önce oda eklemen gerekebilir!
    }

    @Test
    public void testGetRoomById() throws Exception {
        Room room = createTestRoom();
        RoomDAO.addRoom(room);

        // Son eklenen odayı bulalım
        List<Room> all = RoomDAO.getAllRooms();
        Room last = all.get(all.size() - 1);

        Room found = RoomDAO.getRoomById(last.getId());
        assertNotNull(found, "Oda ID ile bulunamadı!");
        assertEquals(last.getRoomNumber(), found.getRoomNumber(), "Oda numarası yanlış!");
    }

    @Test
   public void testUpdateRoom() throws Exception {
       Room room = createTestRoom();
       RoomDAO.addRoom(room);

       // Son eklenen odayı bulup güncelle
       List<Room> all = RoomDAO.getAllRooms();
       Room last = all.get(all.size() - 1);

       last.setType("Suite");
       last.setStatus("occupied"); // Sadece 'empty', 'occupied', 'maintenance' olabilir!
       last.setPrice(1234.0);

       RoomDAO.updateRoom(last);

       Room updated = RoomDAO.getRoomById(last.getId());
       assertNotNull(updated, "Güncellenen oda bulunamadı!");
       assertEquals("Suite", updated.getType(), "Oda tipi güncellenmedi!");
       assertEquals("occupied", updated.getStatus(), "Oda durumu güncellenmedi!");
       assertEquals(1234.0, updated.getPrice(), "Oda fiyatı güncellenmedi!");
   }



    @Test
    public void testDeleteRoom() throws Exception {
        Room room = createTestRoom();
        RoomDAO.addRoom(room);

        // Son eklenen odayı bul
        List<Room> all = RoomDAO.getAllRooms();
        Room last = all.get(all.size() - 1);

        RoomDAO.deleteRoom(last.getId());

        Room deleted = RoomDAO.getRoomById(last.getId());
        assertNull(deleted, "Oda silinemedi!");
    }
}
