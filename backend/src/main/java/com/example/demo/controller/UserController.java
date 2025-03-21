package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            
            // Debug logging
            System.out.println("\n=== Login Debug Info ===");
            System.out.println("Login attempt with:");
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            
            User user = userRepository.findByEmail(email);
            
            if (user == null) {
                System.out.println("No user found with email: " + email);
                return ResponseEntity.badRequest().body("User not found");
            }

            System.out.println("\nFound user in database:");
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password in DB: " + user.getPassword());
            System.out.println("Name: " + user.getName());
            System.out.println("Role: " + user.getRole());
            
            boolean passwordMatches = password.equals(user.getPassword());
            System.out.println("Passwords match: " + passwordMatches);
            
            if (passwordMatches) {
                Map<String, Object> response = new HashMap<>();
                response.put("name", user.getName());
                response.put("email", user.getEmail());
                response.put("role", user.getRole());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("Invalid password");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }
} 