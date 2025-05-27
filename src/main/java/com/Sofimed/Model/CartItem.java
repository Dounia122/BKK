package com.Sofimed.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column
    private Integer quantity;

    @Column
    private Double prix;
    
    @Column(name = "prix_unitaire")
    private Double prixUnitaire;
    
    @Column(name = "remise_pourcentage")
    private Double remisePourcentage;
    
    @Column(name = "remise_montant")
    private Double remiseMontant;
    
    @Column(name = "prix_apres_remise")
    private Double prixApresRemise;
    
    @Column(name = "total_item")
    private Double totalItem;
    
    
 // Getters
    public Double getPrixUnitaire() {
        return prixUnitaire;
    }
    
    public Double getRemisePourcentage() {
        return remisePourcentage;
    }
    
    public Double getRemiseMontant() {
        return remiseMontant;
    }
    
    public Double getPrixApresRemise() {
        return prixApresRemise;
    }
    
    public Double getTotalItem() {
        return totalItem;
    }
    
    // Setters
    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    
    public void setRemisePourcentage(Double remisePourcentage) {
        this.remisePourcentage = remisePourcentage;
    }
    
    public void setRemiseMontant(Double remiseMontant) {
        this.remiseMontant = remiseMontant;
    }
    
    public void setPrixApresRemise(Double prixApresRemise) {
        this.prixApresRemise = prixApresRemise;
    }
    
    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    // Constructeur par défaut
    public CartItem() {}

    // Getters
    public Long getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public Produit getProduit() {
        return produit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrix() {
        return prix;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
        if (produit != null) {
            this.prix = produit.getPrix();
        }
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}