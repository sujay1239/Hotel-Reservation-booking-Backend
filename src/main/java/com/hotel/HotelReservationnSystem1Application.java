package com.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Hotel Reservation System.
 *
 * Class name matches the artifactId convention:
 *   artifactId : Hotel-Reservationn-System1
 *   class name : HotelReservationnSystem1Application
 *
 * Place this file at:
 *   src/main/java/com/hotel/HotelReservationnSystem1Application.java
 *
 * DELETE the old HotelReservationApplication.java file.
 */
@SpringBootApplication
public class HotelReservationnSystem1Application {

    public static void main(String[] args) {
        SpringApplication.run(HotelReservationnSystem1Application.class, args);
        System.out.println("==========================================");
        System.out.println("  Hotel Reservation System Started!");
        System.out.println("  API  -> http://localhost:8080");
        System.out.println("  UI   -> http://localhost:3000");
        System.out.println("==========================================");
    }
}
