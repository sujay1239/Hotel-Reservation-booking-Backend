package com.hotel.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Booking Entity - Represents a hotel room booking
 */
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique reference e.g. BK-20240101-00123
    @Column(nullable = false, unique = true)
    private String bookingReference;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private int numberOfNights;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    // Status: PENDING, CONFIRMED, CANCELLED, COMPLETED
    @Column(nullable = false)
    private String status = "PENDING";

    @Column(length = 500)
    private String specialRequests;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    // ===================== Constructors =====================

    public Booking() {}

    public Booking(Long id, String bookingReference, User user, Room room,
                   LocalDate checkInDate, LocalDate checkOutDate, int numberOfNights,
                   BigDecimal totalAmount, String status, String specialRequests) {
        this.id = id;
        this.bookingReference = bookingReference;
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfNights = numberOfNights;
        this.totalAmount = totalAmount;
        this.status = status;
        this.specialRequests = specialRequests;
    }

    // ===================== Getters =====================

    public Long getId() { return id; }
    public String getBookingReference() { return bookingReference; }
    public User getUser() { return user; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public int getNumberOfNights() { return numberOfNights; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getSpecialRequests() { return specialRequests; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Payment getPayment() { return payment; }

    // ===================== Setters =====================

    public void setId(Long id) { this.id = id; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public void setUser(User user) { this.user = user; }
    public void setRoom(Room room) { this.room = room; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setNumberOfNights(int numberOfNights) { this.numberOfNights = numberOfNights; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setPayment(Payment payment) { this.payment = payment; }

    @Override
    public String toString() {
        return "Booking{id=" + id + ", ref='" + bookingReference + "', status='" + status + "'}";
    }
}
