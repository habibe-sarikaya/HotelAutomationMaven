package com.habibe.hotelautomationmaven.model;

import com.habibe.hotelautomationmaven.model.Reservation;
import com.habibe.hotelautomationmaven.model.ReservationDAO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.Date;

public class ReservationDAOTest {

    // Gerçek veritabanındaki mevcut bir müşteri ve oda ID'si kullan!
    // Bunları kendi veritabanına göre DÜZENLE!
    private static final int TEST_CUSTOMER_ID = 7; // var olan müşteri ID
    private static final int TEST_ROOM_ID = 15;     // var olan oda ID

    private Reservation createTestReservation() {
        Reservation r = new Reservation();
        r.setCustomerId(TEST_CUSTOMER_ID);
        r.setRoomId(TEST_ROOM_ID);

        // Bugün ve yarın olacak şekilde tarih ayarlıyoruz
        Calendar cal = Calendar.getInstance();
        Date checkin = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date checkout = cal.getTime();

        r.setCheckin(checkin);
        r.setCheckout(checkout);
        r.setStatus("reserved");
        return r;
    }

    @Test
    public void testAddReservation() {
        Reservation reservation = createTestReservation();
        assertDoesNotThrow(() -> ReservationDAO.addReservation(reservation), "Rezervasyon eklenirken hata oluştu!");
    }

    @Test
    public void testGetAllReservations() {
        List<Reservation> reservations = ReservationDAO.getAllReservations();
        assertNotNull(reservations, "Rezervasyonlar null geldi!");
    }

    @Test
    public void testIsRoomAvailable() {
        Reservation reservation = createTestReservation();
        ReservationDAO.addReservation(reservation);

        // Normalde eklediğimiz rezervasyon ile çakışmayan bir tarih seçersek true döner
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date checkin2 = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date checkout2 = cal.getTime();

        boolean available = ReservationDAO.isRoomAvailable(TEST_ROOM_ID, checkin2, checkout2);
        assertTrue(available, "Oda uygun olması gerekirken uygun değil görünüyor!");
    }

    @Test
    public void testUpdateReservation() {
        // Önce yeni rezervasyon ekle
        Reservation reservation = createTestReservation();
        ReservationDAO.addReservation(reservation);

        // Eklediğimiz rezervasyonu bul (son eklenenin ID’si büyük olur genelde)
        List<Reservation> all = ReservationDAO.getAllReservations();
        Reservation last = all.get(all.size() - 1);

        // Durumu değiştirip güncelle
        last.setStatus("checked_in");
        ReservationDAO.updateReservation(last);

        // Güncellenen rezervasyonu tekrar getir ve kontrol et
        List<Reservation> updatedList = ReservationDAO.getAllReservations();
        Reservation updated = updatedList.stream().filter(r -> r.getId() == last.getId()).findFirst().orElse(null);

        assertNotNull(updated, "Güncellenen rezervasyon bulunamadı!");
        assertEquals("checked_in", updated.getStatus(), "Rezervasyon durumu güncellenemedi!");
    }

    @Test
    public void testDeleteReservation() {
        Reservation reservation = createTestReservation();
        ReservationDAO.addReservation(reservation);

        // Eklediğimiz rezervasyonu bul
        List<Reservation> all = ReservationDAO.getAllReservations();
        Reservation last = all.get(all.size() - 1);

        // Sil
        assertDoesNotThrow(() -> ReservationDAO.deleteReservation(last.getId()), "Rezervasyon silinemedi!");

        // Tekrar getir ve silindi mi kontrol et
        List<Reservation> afterDelete = ReservationDAO.getAllReservations();
        boolean exists = afterDelete.stream().anyMatch(r -> r.getId() == last.getId());
        assertFalse(exists, "Rezervasyon silinmedi!");
    }

    @Test
    public void testUpdateReservationStatus() {
        Reservation reservation = createTestReservation();
        ReservationDAO.addReservation(reservation);

        // Eklenen rezervasyonu bul
        List<Reservation> all = ReservationDAO.getAllReservations();
        Reservation last = all.get(all.size() - 1);

        // Durumu değiştir
        String newStatus = "cancelled";
        ReservationDAO.updateReservationStatus(last.getId(), newStatus);

        // Tekrar bul ve kontrol et
        List<Reservation> updatedList = ReservationDAO.getAllReservations();
        Reservation updated = updatedList.stream().filter(r -> r.getId() == last.getId()).findFirst().orElse(null);

        assertNotNull(updated, "Güncellenen rezervasyon bulunamadı!");
        assertEquals(newStatus, updated.getStatus(), "Rezervasyon durumu güncellenemedi!");
    }

    // Eğer isRoomAvailableForEdit fonksiyonunu da test etmek istersen aynı mantıkla ekleyebiliriz.
}
