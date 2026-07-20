package com.carinsurance.Car.Insurance.Application.service;

import com.carinsurance.Car.Insurance.Application.dto.AuthResponse;
import com.carinsurance.Car.Insurance.Application.dto.LoginRequest;
import com.carinsurance.Car.Insurance.Application.dto.SignupRequest;
import com.carinsurance.Car.Insurance.Application.dto.UserDTO;
import com.carinsurance.Car.Insurance.Application.exception.DuplicateResourceException;
import com.carinsurance.Car.Insurance.Application.exception.InvalidCredentialsException;
import com.carinsurance.Car.Insurance.Application.model.User;
import com.carinsurance.Car.Insurance.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Signup new user
    public AuthResponse signup(SignupRequest request) {

        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    "Username already exists: " + request.getUsername());
        }

        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists: " + request.getEmail());
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole("USER");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // Create response
        AuthResponse response = new AuthResponse();
        response.setMessage("User registered successfully");
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setSuccess(true);

        return response;
    }

    // Login user
    public AuthResponse login(LoginRequest request) {

        // Find user by username or email
        User user = userRepository.findByUsernameOrEmail(
                request.getUsernameOrEmail(),
                request.getUsernameOrEmail()
        ).orElseThrow(() -> new InvalidCredentialsException(
                "Invalid username or password"));

        // Check if user is active
        if (!user.isActive()) {
            throw new InvalidCredentialsException("Account is deactivated");
        }

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Create response
        AuthResponse response = new AuthResponse();
        response.setMessage("Login successful");
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setSuccess(true);

        return response;
    }

    // Convert User to UserDTO
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
