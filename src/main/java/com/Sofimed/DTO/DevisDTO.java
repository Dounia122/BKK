package com.Sofimed.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisDTO {
    private Long id;
    private String reference;
    private String title;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String paymentMethod;
    private String commentaire;
    private CommercialDTO commercial;
    private Integer unreadMessages;
    private String email;

    // Getters
    public Long getId() {
        return id;
    }
    
    public String getReference() {
        return reference;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public String getCommentaire() {
        return commentaire;
    }
    
    public CommercialDTO getCommercial() {
        return commercial;
    }
    
    public Integer getUnreadMessages() {
        return unreadMessages;
    }
    
    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public void setCommercial(CommercialDTO commercial) {
        this.commercial = commercial;
    }
    
    public void setUnreadMessages(Integer unreadMessages) {
        this.unreadMessages = unreadMessages;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}