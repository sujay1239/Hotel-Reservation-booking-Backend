package com.hotel.dto;

/**
 * AuthResponse - DTO returned after successful login or register
 */
public class AuthResponse {

    private String token;
    private String email;
    private String role;
    private String name;
    private Long userId;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, String email, String role, String name, Long userId) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.name = name;
        this.userId = userId;
    }

    // Getters
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getName() { return name; }
    public Long getUserId() { return userId; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setName(String name) { this.name = name; }
    public void setUserId(Long userId) { this.userId = userId; }
}
