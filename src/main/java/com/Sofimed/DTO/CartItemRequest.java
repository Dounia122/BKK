package com.Sofimed.DTO;



public class CartItemRequest {
    private Long produitId; // Doit correspondre au nom envoyé par le frontend
    private int quantity;
    private Double remisePourcentage;


    // Getters et Setters
    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

