package com.habibe.hotelautomationmaven.model;

import java.util.Date;

public class Reservation {
    private int id;
    private int customerId;
    private int roomId;
    private Date checkin;
    private Date checkout;
    private String status;

    // Getter ve Setter'lar
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public Date getCheckin() { return checkin; }
    public void setCheckin(Date checkin) { this.checkin = checkin; }

    public Date getCheckout() { return checkout; }
    public void setCheckout(Date checkout) { this.checkout = checkout; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
