package com.Sofimed.Controller;

import com.Sofimed.DTO.AuthResponse;
import com.Sofimed.DTO.LoginRequest;
import com.Sofimed.DTO.RegisterRequest;
import com.Sofimed.Model.Client;
import com.Sofimed.Service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login attempt for user: {}", loginRequest.getUsername());
            
            AuthResponse response = userService.authenticate(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
            );
            
            logger.info("Authentication successful for user: {}", loginRequest.getUsername());
            return ResponseEntity.ok()
                .body(Map.of(
                    "token", response.getToken(),
                    "user", response.getUser(),
                    "message", "Connexion réussie"
                ));
            
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Nom d'utilisateur ou mot de passe incorrect"));
        } catch (Exception e) {
            logger.error("Authentication error for user: {}, error: {}", 
                loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Erreur lors de l'authentification"));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok()
            .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS")
            .header("Access-Control-Allow-Headers", "*")
            .build();
    }}