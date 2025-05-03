package com.Sofimed.DTO;



public class AuthResponse {
    private String token;
    private UserDTO user;

    // Default constructor
    public AuthResponse() {
    }

    // All-args constructor
    public AuthResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }

    // Getter for token
    public String getToken() {
        return token;
    }

    // Setter for token
    public void setToken(String token) {
        this.token = token;
    }

    // Getter for user
    public UserDTO getUser() {
        return user;
    }

    // Setter for user
    public void setUser(UserDTO user) {
        this.user = user;
    }
}