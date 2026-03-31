package com.hotel.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Column(length = 500)
    private String description;

    private String amenities;

    private String imageUrl;

    @Column(nullable = false)
    private boolean available = true;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    public Room() {}

    public Room(Long id, String roomNumber, String roomType, int capacity,
                BigDecimal pricePerNight, String description, String amenities,
                String imageUrl, boolean available) {
        this.id            = id;
        this.roomNumber    = roomNumber;
        this.roomType      = roomType;
        this.capacity      = capacity;
        this.pricePerNight = pricePerNight;
        this.description   = description;
        this.amenities     = amenities;
        this.imageUrl      = imageUrl;
        this.available     = available;
    }

    public Long getId()                  { return id; }
    public String getRoomNumber()        { return roomNumber; }
    public String getRoomType()          { return roomType; }
    public int getCapacity()             { return capacity; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public String getDescription()       { return description; }
    public String getAmenities()         { return amenities; }
    public String getImageUrl()          { return imageUrl; }
    public boolean isAvailable()         { return available; }
    public List<Booking> getBookings()   { return bookings; }

    public void setId(Long id)                       { this.id = id; }
    public void setRoomNumber(String roomNumber)     { this.roomNumber = roomNumber; }
    public void setRoomType(String roomType)         { this.roomType = roomType; }
    public void setCapacity(int capacity)            { this.capacity = capacity; }
    public void setPricePerNight(BigDecimal p)       { this.pricePerNight = p; }
    public void setDescription(String description)   { this.description = description; }
    public void setAmenities(String amenities)       { this.amenities = amenities; }
    public void setImageUrl(String imageUrl)         { this.imageUrl = imageUrl; }
    public void setAvailable(boolean available)      { this.available = available; }
    public void setBookings(List<Booking> bookings)  { this.bookings = bookings; }

    @Override
    public String toString() {
        return "Room{id=" + id + ", roomNumber='" + roomNumber + "', type='" + roomType + "'}";
    }
}