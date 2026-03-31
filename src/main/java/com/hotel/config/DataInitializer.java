package com.hotel.config;

import com.hotel.model.Room;
import com.hotel.model.User;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

/**
 * DataInitializer
 * Automatically inserts sample rooms and users into the database
 * the FIRST time the application starts (only if tables are empty).
 * You do NOT need to run data.sql manually.
 */
@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            // ---- Insert Users (only if none exist) ----
            if (userRepository.count() == 0) {

                // Admin user
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setEmail("admin@hotel.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setPhone("9999999999");
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);

                // Guest user 1
                User john = new User();
                john.setFirstName("John");
                john.setLastName("Doe");
                john.setEmail("john@example.com");
                john.setPassword(passwordEncoder.encode("password123"));
                john.setPhone("9876543210");
                john.setRole("ROLE_USER");
                userRepository.save(john);

                // Guest user 2
                User jane = new User();
                jane.setFirstName("Jane");
                jane.setLastName("Smith");
                jane.setEmail("jane@example.com");
                jane.setPassword(passwordEncoder.encode("password123"));
                jane.setPhone("9123456789");
                jane.setRole("ROLE_USER");
                userRepository.save(jane);

                System.out.println("✅ Sample users inserted into database.");
            } else {
                System.out.println("ℹ️  Users already exist, skipping seed.");
            }

            // ---- Insert Rooms (only if none exist) ----
            if (roomRepository.count() == 0) {

                String imgSingle = "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=500";
                String imgDouble = "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=500";
                String imgDeluxe = "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=500";
                String imgSuite  = "https://images.unsplash.com/photo-1631049421450-348ccd7f8949?w=500";

                roomRepository.save(buildRoom("101", "SINGLE", 1,
                        new BigDecimal("1500.00"),
                        "Cozy single room with city view",
                        "WiFi,AC,TV,Mini Bar", imgSingle));

                roomRepository.save(buildRoom("102", "SINGLE", 1,
                        new BigDecimal("1500.00"),
                        "Comfortable single room with garden view",
                        "WiFi,AC,TV", imgSingle));

                roomRepository.save(buildRoom("201", "DOUBLE", 2,
                        new BigDecimal("2500.00"),
                        "Spacious double room with king bed",
                        "WiFi,AC,TV,Mini Bar,Balcony", imgDouble));

                roomRepository.save(buildRoom("202", "DOUBLE", 2,
                        new BigDecimal("2500.00"),
                        "Double room with two queen beds",
                        "WiFi,AC,TV,Balcony", imgDouble));

                roomRepository.save(buildRoom("301", "DELUXE", 3,
                        new BigDecimal("4000.00"),
                        "Deluxe room with sea view and lounge area",
                        "WiFi,AC,TV,Mini Bar,Balcony,Jacuzzi", imgDeluxe));

                roomRepository.save(buildRoom("302", "DELUXE", 3,
                        new BigDecimal("4000.00"),
                        "Deluxe room with premium amenities",
                        "WiFi,AC,TV,Mini Bar,Jacuzzi,Room Service", imgDeluxe));

                roomRepository.save(buildRoom("401", "SUITE", 4,
                        new BigDecimal("8000.00"),
                        "Luxury suite with panoramic city view",
                        "WiFi,AC,TV,Mini Bar,Balcony,Jacuzzi,Living Room,Kitchen", imgSuite));

                roomRepository.save(buildRoom("402", "SUITE", 6,
                        new BigDecimal("12000.00"),
                        "Presidential suite with private pool",
                        "WiFi,AC,TV,Pool,Jacuzzi,Living Room,Kitchen,Butler", imgSuite));

                System.out.println("✅ Sample rooms inserted into database.");
            } else {
                System.out.println("ℹ️  Rooms already exist, skipping seed.");
            }
        };
    }

    /** Helper to build a Room object */
    private Room buildRoom(String number, String type, int capacity,
                            BigDecimal price, String description,
                            String amenities, String imageUrl) {
        Room room = new Room();
        room.setRoomNumber(number);
        room.setRoomType(type);
        room.setCapacity(capacity);
        room.setPricePerNight(price);
        room.setDescription(description);
        room.setAmenities(amenities);
        room.setImageUrl(imageUrl);
        room.setAvailable(true);
        return room;
    }
}
