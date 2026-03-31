package com.hotel.service;

import com.hotel.dto.BookingRequest;
import com.hotel.model.Booking;
import com.hotel.model.Room;
import com.hotel.model.User;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

/**
 * BookingService - Creates, cancels, and retrieves bookings
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        LocalDate checkIn = request.getCheckInDate();
        LocalDate checkOut = request.getCheckOutDate();

        if (checkIn.isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new RuntimeException("Check-out must be after check-in");
        }

        List<Room> availableRooms = roomRepository.findAvailableRooms(checkIn, checkOut);
        boolean isAvailable = availableRooms.stream().anyMatch(r -> r.getId().equals(room.getId()));
        if (!isAvailable) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setBookingReference(generateBookingReference());
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setNumberOfNights((int) nights);
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");
        booking.setSpecialRequests(request.getSpecialRequests());

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public Booking getBookingByReference(String reference) {
        return bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + reference));
    }

    public Booking cancelBooking(Long bookingId, Long userId) {
        Booking booking = getBookingById(bookingId);

        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You cannot cancel this booking");
        }
        if (booking.getStatus().equals("CANCELLED")) {
            throw new RuntimeException("Booking is already cancelled");
        }

        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    private String generateBookingReference() {
        String date = LocalDate.now().toString().replace("-", "");
        String random = String.format("%05d", new Random().nextInt(99999));
        return "BK-" + date + "-" + random;
    }
}
