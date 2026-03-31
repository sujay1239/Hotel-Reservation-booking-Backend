package com.hotel.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payment Entity - Mock payment record for a booking
 */
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique transaction ID e.g. TXN-ABC12345
    @Column(nullable = false, unique = true)
    private String transactionId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    // CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, CASH
    @Column(nullable = false)
    private String paymentMethod;

    // PENDING, SUCCESS, FAILED, REFUNDED
    @Column(nullable = false)
    private String status = "PENDING";

    // Last 4 digits of card (mock display only)
    private String cardLastFour;

    @Column(nullable = false, updatable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    private LocalDateTime refundDate;

    // ===================== Constructors =====================

    public Payment() {}

    public Payment(Long id, String transactionId, Booking booking, BigDecimal amount,
                   String paymentMethod, String status, String cardLastFour) {
        this.id = id;
        this.transactionId = transactionId;
        this.booking = booking;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.cardLastFour = cardLastFour;
    }

    // ===================== Getters =====================

    public Long getId() { return id; }
    public String getTransactionId() { return transactionId; }
    public Booking getBooking() { return booking; }
    public BigDecimal getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public String getCardLastFour() { return cardLastFour; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public LocalDateTime getRefundDate() { return refundDate; }

    // ===================== Setters =====================

    public void setId(Long id) { this.id = id; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setStatus(String status) { this.status = status; }
    public void setCardLastFour(String cardLastFour) { this.cardLastFour = cardLastFour; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public void setRefundDate(LocalDateTime refundDate) { this.refundDate = refundDate; }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", txnId='" + transactionId + "', status='" + status + "'}";
    }
}
