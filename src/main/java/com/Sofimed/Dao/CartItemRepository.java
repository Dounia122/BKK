package com.Sofimed.Dao;

import com.Sofimed.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    // Trouver tous les items d'un panier spécifique
    List<CartItem> findByCartId(Long cartId);
    
    // Trouver un item spécifique dans un panier
    Optional<CartItem> findByCartIdAndId(Long cartId, Long itemId);
    
    // Trouver un item par produit dans un panier spécifique
    Optional<CartItem> findByCartIdAndProduitId(Long cartId, Long produitId);
    
    // Compter le nombre d'items dans un panier
    Long countByCartId(Long cartId);
    
    // Supprimer tous les items d'un panier
    void deleteByCartId(Long cartId);
    
    
    
    
    // Calculer le total du panier
    @Query("SELECT SUM(ci.prix * ci.quantity) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Double calculateCartTotal(@Param("cartId") Long cartId);
    
    // Trouver les items avec une quantité supérieure à un seuil
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.quantity > :minQuantity")
    List<CartItem> findItemsWithQuantityGreaterThan(
        @Param("cartId") Long cartId, 
        @Param("minQuantity") Integer minQuantity
    );
    

    
    // Mettre à jour la quantité d'un item
    @Query("UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.id = :itemId AND ci.cart.id = :cartId")
    void updateItemQuantity(
        @Param("cartId") Long cartId, 
        @Param("itemId") Long itemId, 
        @Param("quantity") Integer quantity
    );
}