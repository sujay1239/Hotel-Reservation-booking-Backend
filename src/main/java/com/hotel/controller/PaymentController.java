package com.hotel.controller;

import com.hotel.dto.PaymentRequest;
import com.hotel.model.Payment;
import com.hotel.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PaymentController - Mock payment processing
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments/process/{bookingId}")
    public ResponseEntity<Payment> processPayment(@PathVariable Long bookingId,
                                                   @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request, bookingId));
    }

    @PostMapping("/payments/refund/{bookingId}")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.processRefund(bookingId));
    }

    @GetMapping("/payments/booking/{bookingId}")
    public ResponseEntity<Payment> getPaymentByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentByBookingId(bookingId));
    }

    @GetMapping("/admin/payments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
