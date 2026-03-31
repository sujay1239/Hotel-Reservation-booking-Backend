package com.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User Entity - Represents a registered user (guest or admin)
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    // Role: ROLE_USER or ROLE_ADMIN
    @Column(nullable = false)
    private String role = "ROLE_USER";

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // One user can have many bookings
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> bookings;

    // ===================== Constructors =====================

    public User() {}

    public User(Long id, String firstName, String lastName, String email,
                String password, String phone, String role, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.createdAt = createdAt;
    }

    // ===================== Getters =====================

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Booking> getBookings() { return bookings; }

    // ===================== Setters =====================

    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRole(String role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }

    @Override
    public String toString() {
        return "User{id=" + id + ", email='" + email + "', role='" + role + "'}";
    }
}
