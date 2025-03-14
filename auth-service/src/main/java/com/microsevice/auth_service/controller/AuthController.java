package com.microsevice.auth_service.controller;


import com.microsevice.auth_service.Exception.UserAlredyExistException;
import com.microsevice.auth_service.config.JwtUtil;
import com.microsevice.auth_service.dto.AuthRequest;
import com.microsevice.auth_service.model.User;
import com.microsevice.auth_service.repository.UserRepository;
import com.microsevice.auth_service.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        try {
            User newuser = service.saveUser(user);
            return ResponseEntity.ok(newuser);
        } catch (UserAlredyExistException e) {
            response.put("error", "user already exist");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {

        System.out.println("received email :" + request.getEmail());
        System.out.println("received password:" + request.getPassword());
        return service.login(request, response);
    }

    @GetMapping("/current-user")
    public ResponseEntity<String> getCurrentUser(@CookieValue(name = "jwt", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("no token found");
        }

        String email = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(email);
    }


}
