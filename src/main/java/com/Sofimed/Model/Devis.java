package com.Sofimed.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.Sofimed.DTO.DevisStatus;

@Entity
@Table(name = "deviss")
public class Devis {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @OneToOne
	    @JoinColumn(name = "cart_id", nullable = false)
	    private Cart cart;
	    
	    @ManyToOne
	    @JoinColumn(name = "commercial_id")
	    private Commercial commercial;
	    
	    @Column(name = "reference", unique = true, nullable = false)
	    private String reference;
	    
	    @Column(name = "payment_method", nullable = false)
	    private String paymentMethod;
	    
	    private String commentaire;
	    
	    @Column(name = "created_at", nullable = false)
	    private LocalDateTime createdAt;
	    
	    @Column(name = "status", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private DevisStatus status;
	    
	    @Column(name = "totale", precision = 10, scale = 2)
	    private BigDecimal totale;
	    
	    
	    
	    
	    
	    

    // Constructeur par défaut
    public Devis() {
    }

    // Constructeur avec tous les paramètres
    public Devis(Long id, Cart cart, Commercial commercial, String paymentMethod, 
                 String commentaire, LocalDateTime createdAt, DevisStatus status) {
        this.id = id;
        this.cart = cart;
        this.commercial = commercial;
        this.paymentMethod = paymentMethod;
        this.commentaire = commentaire;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }
    
  
    

    public Commercial getCommercial() {
        return commercial;
    }
    
    public String getReference() {
        return reference;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public DevisStatus getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setReference(String  id) {
        this.reference = id;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setCommercial(Commercial commercial) {
        this.commercial = commercial;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(DevisStatus status) {
        this.status = status;
    }

    @PrePersist 
    protected void onCreate() { 
        createdAt = LocalDateTime.now(); 
        status = DevisStatus.EN_ATTENTE; 
    }
}