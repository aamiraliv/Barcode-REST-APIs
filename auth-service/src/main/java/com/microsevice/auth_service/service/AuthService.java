package com.microsevice.auth_service.service;


import com.microsevice.auth_service.Exception.UserAlredyExistException;
import com.microsevice.auth_service.config.JwtUtil;
import com.microsevice.auth_service.dto.AuthRequest;
import com.microsevice.auth_service.dto.AuthResponse;
import com.microsevice.auth_service.model.User;
import com.microsevice.auth_service.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public User saveUser(User user) {
        if (repository.existsByEmail(user.getEmail())){
            throw new UserAlredyExistException("User with email " + user.getEmail() + " already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

//    public ResponseEntity<?> verifyLogin(AuthRequest user) {
//        Optional<User> existingUser = repository.findByEmail(user.getEmail());
//
//        if (existingUser.isPresent() &&
//                passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())
//        ) {
//            String token = jwtUtil.generateToken(user.getEmail());
//            return ResponseEntity.ok(Map.of("token",token));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
//    }

    public ResponseEntity<?> login(AuthRequest request, HttpServletResponse response) {
        try {
            if (request == null || request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password must be provided");
            }

            System.out.println("🔹 Received Login Request: " + request.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
            );

            System.out.println("✅ Authentication Successful for: " + request.getEmail());

            String token = jwtUtil.generateToken(request.getEmail());
            System.out.println("🔹 Generated Token: " + token);

            String email = jwtUtil.extractEmail(token);
            System.out.println("🔹 Extracted Email from Token: " + email);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            return ResponseEntity.ok(new AuthResponse(token,email));
        }catch (Exception e){
            System.out.println("❌ Authentication Failed: " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }

    public Object logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt",null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok("logout successfully");
    }
}
