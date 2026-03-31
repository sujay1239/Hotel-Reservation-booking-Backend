package com.hotel.service;

import com.hotel.config.JwtUtil;
import com.hotel.dto.AuthRequest;
import com.hotel.dto.AuthResponse;
import com.hotel.dto.RegisterRequest;
import com.hotel.model.User;
import com.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthService - Handles register and login
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole("ROLE_USER");

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("name", user.getFirstName() + " " + user.getLastName());
        String token = jwtUtil.generateToken(userDetails, claims);

        return new AuthResponse(token, user.getEmail(), user.getRole(),
                user.getFirstName() + " " + user.getLastName(), user.getId());
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("name", user.getFirstName() + " " + user.getLastName());
        String token = jwtUtil.generateToken(userDetails, claims);

        return new AuthResponse(token, user.getEmail(), user.getRole(),
                user.getFirstName() + " " + user.getLastName(), user.getId());
    }
}
