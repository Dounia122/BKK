package com.Sofimed.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevisDTO {
	private Long id;
    private String reference;
    private String status;
    private LocalDateTime createdAt;
    private String paymentMethod;
    private String commentaire;
    private CommercialDTO commercial;
    private Integer unreadMessages;
    private String email;
    
    // Custom constructor for basic initialization
    public DevisDTO(Long id, String reference, String status, LocalDateTime createdAt) {
        this.id = id;
        this.reference = reference;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    public DevisDTO(
            Long id,
            String reference,
            String status,
            LocalDateTime createdAt,
            String paymentMethod,
            String commentaire,
            CommercialDTO commercial,
            Integer unreadMessages,
            String email
        ) {
            this.id = id;
            this.reference = reference;
            this.status = status;
            this.createdAt = createdAt;
            this.paymentMethod = paymentMethod;
            this.commentaire = commentaire;
            this.commercial = commercial;
            this.unreadMessages = unreadMessages;
            this.email = email;
        }
    
    // Method to check if the devis has a commercial assigned
    public boolean hasCommercial() {
        return commercial != null;
    }
    
    // Method to get formatted creation date
    public String getFormattedCreationDate() {
        if (createdAt == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(formatter);
    }
    
    // Method to get status display name
    public String getStatusDisplayName() {
        if (status == null) return "UNKNOWN";
        switch (status) {
            case "EN_ATTENTE": return "En attente";
            case "EN_COURS": return "En cours";
            case "TERMINÉ": return "Terminé";
            default: return status;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }
    
    
   
    public String getReference() {
        return reference;
    }
   
    
    public String getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
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
    
   
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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