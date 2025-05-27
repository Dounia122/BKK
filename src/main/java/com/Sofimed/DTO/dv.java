package com.Sofimed.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class dv {
    private Long id;
    private Long cartId; // Ajout du champ cartId
    private String reference;
    private String paymentMethod;
    private String commentaire;
    private LocalDateTime createdAt;
    private String status;
    private BigDecimal totale;

    // Constructeur avec cartId
    public dv(Long id, Long cartId, String reference, String paymentMethod, String commentaire, LocalDateTime createdAt, DevisStatus devisStatus, BigDecimal totale) {
        this.id = id;
        this.cartId = cartId;
        this.reference = reference;
        this.paymentMethod = paymentMethod;
        this.commentaire = commentaire;
        this.createdAt = createdAt;
        
        this.totale = totale;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }
}