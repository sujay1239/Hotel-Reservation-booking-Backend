package com.hotel.dto;

import java.time.LocalDate;

/**
 * BookingRequest - DTO for creating a new booking
 */
public class BookingRequest {

    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String specialRequests;

    // Constructors
    public BookingRequest() {}

    public BookingRequest(Long roomId, LocalDate checkInDate,
                          LocalDate checkOutDate, String specialRequests) {
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.specialRequests = specialRequests;
    }

    // Getters
    public Long getRoomId() { return roomId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public String getSpecialRequests() { return specialRequests; }

    // Setters
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
}
