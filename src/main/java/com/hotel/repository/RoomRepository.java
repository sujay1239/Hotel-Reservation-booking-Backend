package com.hotel.repository;

import com.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByAvailableTrue();

    List<Room> findByRoomTypeAndAvailableTrue(String roomType);

    @Query("SELECT r FROM Room r WHERE r.available = true AND r.id NOT IN " +
           "(SELECT b.room.id FROM Booking b WHERE b.status != 'CANCELLED' AND " +
           "b.checkInDate < :checkOut AND b.checkOutDate > :checkIn)")
    List<Room> findAvailableRooms(@Param("checkIn") LocalDate checkIn,
                                   @Param("checkOut") LocalDate checkOut);

    @Query("SELECT r FROM Room r WHERE r.available = true AND r.roomType = :roomType AND r.id NOT IN " +
           "(SELECT b.room.id FROM Booking b WHERE b.status != 'CANCELLED' AND " +
           "b.checkInDate < :checkOut AND b.checkOutDate > :checkIn)")
    List<Room> findAvailableRoomsByType(@Param("roomType") String roomType,
                                         @Param("checkIn") LocalDate checkIn,
                                         @Param("checkOut") LocalDate checkOut);

    boolean existsByRoomNumber(String roomNumber);
}
