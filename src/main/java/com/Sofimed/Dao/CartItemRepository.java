package com.Sofimed.Dao;

import com.Sofimed.Model.CartItem;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    
  
    @Modifying
    @Transactional
    @Query("UPDATE CartItem ci SET " +
           "ci.prixUnitaire = :prixUnitaire, " +
           "ci.remisePourcentage = :remisePourcentage, " +
           "ci.remiseMontant = :prixUnitaire * (:remisePourcentage / 100), " +
           "ci.prixApresRemise = :prixUnitaire - (:prixUnitaire * (:remisePourcentage / 100)), " +
           "ci.totalItem = (:prixUnitaire - (:prixUnitaire * (:remisePourcentage / 100))) * ci.quantity " +
           "WHERE ci.cart.id = :cartId AND ci.produit.id = :produitId")
    int updateCartItemPriceAndDiscount(
        @Param("cartId") Long cartId,
        @Param("produitId") Long produitId,
        @Param("prixUnitaire") Double prixUnitaire,
        @Param("remisePourcentage") Double remisePourcentage
    );
    
   
    // Compter le nombre d'items dans un panier
    Long countByCartId(Long cartId);
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.produit.id = :produitId")
    Optional<CartItem> findByCartIdAndProduitId(@Param("cartId") Long cartId, @Param("produitId") Long produitId);
    
    
    
    // Supprimer tous les items d'un panier
    @Modifying
    @Query("UPDATE CartItem c SET c.prixUnitaire = :prix, c.remisePourcentage = :remise WHERE c.id = :id")
    void updatePrixAndRemise(@Param("id") Long id, 
                           @Param("prix") Double prix, 
                           @Param("remise") Double remise);

    

    
    // Récupérer tous les items par produitId
    @Query("SELECT ci FROM CartItem ci WHERE ci.produit.id = :produitId")
    List<CartItem> findByProduitId(@Param("produitId") Long produitId);
    @Query("SELECT ci FROM CartItem ci WHERE ci.produit.id = :produitId AND ci.cart.id = :cartId")
    Optional<CartItem> findByProduitIdAndCartId(@Param("produitId") Long produitId, @Param("cartId") Long cartId);
    
    @Modifying
    @Query("UPDATE CartItem c SET c.prixUnitaire = :prixUnitaire, c.remisePourcentage = :remisePourcentage WHERE c.cart.id = :cartId AND c.produit.id = :produitId")
    void updateByCartIdAndProduitId(
      @Param("cartId") Long cartId,
      @Param("produitId") Long produitId,
      @Param("prixUnitaire") Double prixUnitaire,
      @Param("remisePourcentage") Double remisePourcentage
    );


    
    
    
    
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