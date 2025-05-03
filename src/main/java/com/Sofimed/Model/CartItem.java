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