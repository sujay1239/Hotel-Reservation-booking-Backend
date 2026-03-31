package com.hotel.service;

import com.hotel.dto.PaymentRequest;
import com.hotel.model.Booking;
import com.hotel.model.Payment;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * PaymentService - Mock payment processing
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Payment processPayment(PaymentRequest request, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        paymentRepository.findByBookingId(bookingId).ifPresent(p -> {
            if (p.getStatus().equals("SUCCESS")) {
                throw new RuntimeException("Payment already completed for this booking");
            }
        });

        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Payment payment = new Payment();
        payment.setTransactionId(transactionId);
        payment.setBooking(booking);
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setCardLastFour(request.getCardLastFour());

        // Mock: 95% success rate
        boolean paymentSuccess = Math.random() > 0.05;

        if (paymentSuccess) {
            payment.setStatus("SUCCESS");
            booking.setStatus("CONFIRMED");
            bookingRepository.save(booking);
        } else {
            payment.setStatus("FAILED");
        }

        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment processRefund(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("No payment found for booking"));

        if (!payment.getStatus().equals("SUCCESS")) {
            throw new RuntimeException("Cannot refund: payment was not successful");
        }

        payment.setStatus("REFUNDED");
        payment.setRefundDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
