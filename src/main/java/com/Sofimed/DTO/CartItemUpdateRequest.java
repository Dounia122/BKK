package com.Sofimed.DTO;

public class CartItemUpdateRequest {
    private Long cartId;              // Identifiant du panier
    private Long produitId;           // Identifiant du produit
    private Double prixUnitaire;
    private Double remisePourcentage;
    private Integer quantity;

    // Constructeur par défaut
    public CartItemUpdateRequest() {}

    // Constructeur avec paramètres
    public CartItemUpdateRequest(Long cartId, Long produitId, Double prixUnitaire, Double remisePourcentage, Integer quantity) {
        this.cartId = cartId;
        this.produitId = produitId;
        this.prixUnitaire = prixUnitaire;
        this.remisePourcentage = remisePourcentage;
        this.quantity = quantity;
    }

    // Getters
    public Long getCartId() {
        return cartId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public Double getRemisePourcentage() {
        return remisePourcentage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Setters
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setRemisePourcentage(Double remisePourcentage) {
        this.remisePourcentage = remisePourcentage;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // toString pour debug
    @Override
    public String toString() {
        return "CartItemUpdateRequest{" +
                "cartId=" + cartId +
                ", produitId=" + produitId +
                ", prixUnitaire=" + prixUnitaire +
                ", remisePourcentage=" + remisePourcentage +
                ", quantity=" + quantity +
                '}';
    }
}
