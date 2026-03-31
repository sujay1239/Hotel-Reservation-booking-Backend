package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * RoomService - Business logic for room management
 */
@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }

    public List<Room> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }

    public List<Room> searchAvailableRoomsByType(String roomType, LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRoomsByType(roomType, checkIn, checkOut);
    }

    public Room addRoom(Room room) {
        if (roomRepository.existsByRoomNumber(room.getRoomNumber())) {
            throw new RuntimeException("Room number already exists: " + room.getRoomNumber());
        }
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = getRoomById(id);
        existingRoom.setRoomType(updatedRoom.getRoomType());
        existingRoom.setCapacity(updatedRoom.getCapacity());
        existingRoom.setPricePerNight(updatedRoom.getPricePerNight());
        existingRoom.setDescription(updatedRoom.getDescription());
        existingRoom.setAmenities(updatedRoom.getAmenities());
        existingRoom.setImageUrl(updatedRoom.getImageUrl());
        existingRoom.setAvailable(updatedRoom.isAvailable());
        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    public Room toggleAvailability(Long id) {
        Room room = getRoomById(id);
        room.setAvailable(!room.isAvailable());
        return roomRepository.save(room);
    }
}
