package com.Sofimed.DTO;

import java.time.LocalDateTime;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer orderCount;
    private String profileStatus;
    private LocalDateTime lastOrderDate;
    private Boolean isActive;
    
    // Constructeur par défaut
    public ClientDTO() {
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public Integer getOrderCount() {
        return orderCount;
    }
    
    public String getProfileStatus() {
        return profileStatus;
    }
    
    public LocalDateTime getLastOrderDate() {
        return lastOrderDate;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
    
    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }
    
    public void setLastOrderDate(LocalDateTime lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}