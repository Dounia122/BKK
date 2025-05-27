package com.Sofimed.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sofimed.DTO.CartItemUpdateRequest;
import com.Sofimed.Dao.CartItemRepository;
import com.Sofimed.Exception.ResourceNotFoundException;
import com.Sofimed.Model.CartItem;


import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    
    
    @Transactional
    public List<CartItem> batchUpdateCartItems(List<CartItemUpdateRequest> updates) {
        List<CartItem> updatedItems = new ArrayList<>();
        
        for (CartItemUpdateRequest update : updates) {
            CartItem item = cartItemRepository.findByCartIdAndProduitId(
                update.getCartId(), 
                update.getProduitId()
            ).orElseThrow(() -> new RuntimeException("CartItem not found"));
            
            item.setPrixUnitaire(update.getPrixUnitaire());
            item.setRemisePourcentage(update.getRemisePourcentage());
            item.setQuantity(update.getQuantity());
            
            // Recalculer les totaux
            double prixApresRemise = item.getPrixUnitaire() * (1 - item.getRemisePourcentage() / 100);
            item.setPrixApresRemise(prixApresRemise);
            item.setTotalItem(prixApresRemise * item.getQuantity());
            
            updatedItems.add(cartItemRepository.save(item));
        }
        
        return updatedItems;
    }

    
    
    public CartItem updateCartItem(Long cartId, Long produitId, Double prixUnitaire, Double remisePourcentage) {
        CartItem cartItem = cartItemRepository.findByCartIdAndProduitId(cartId, produitId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
            
        cartItem.setPrixUnitaire(prixUnitaire);
        cartItem.setRemisePourcentage(remisePourcentage);
        
        // Calculate new values
        cartItem.setRemiseMontant(prixUnitaire * (remisePourcentage / 100));
        cartItem.setPrixApresRemise(prixUnitaire - cartItem.getRemiseMontant());
        cartItem.setTotalItem(cartItem.getPrixApresRemise() * cartItem.getQuantity());
        
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public boolean updateCartItemDiscount(Long cartId, Long produitId, Double prixUnitaire, Double remisePourcentage) {
        int updatedRows = cartItemRepository.updateCartItemPriceAndDiscount(
            cartId, produitId, prixUnitaire, remisePourcentage
        );
        return updatedRows > 0;
    }
    
    public CartItem updateCartItem(Long cartItemId, Double prixUnitaire, Double remisePourcentage, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException("CartItem non trouvé : " + cartItemId));
            
        if (prixUnitaire != null) {
            item.setPrixUnitaire(prixUnitaire);
        }
        
        if (remisePourcentage != null) {
            item.setRemisePourcentage(remisePourcentage);
            double reduction = (item.getPrixUnitaire() * remisePourcentage) / 100;
            item.setRemiseMontant(reduction);
        }
        
        if (quantity != null) {
            item.setQuantity(quantity);
        }
        
        // Calcul du prix après remise
        double prixApresRemise = item.getPrixUnitaire() - (item.getRemiseMontant() != null ? item.getRemiseMontant() : 0);
        item.setPrixApresRemise(prixApresRemise);
        
        // Calcul du total
        double totalItem = prixApresRemise * item.getQuantity();
        item.setTotalItem(totalItem);
        
        return cartItemRepository.save(item);
    }

    
   
    @Transactional
    public List<CartItem> applyDiscountToCartItems(
        Long cartId, 
        Double pourcentage, 
        Double prixUnitaire, 
        Integer quantity
    ) {
        // Récupérer tous les items du panier
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Aucun item trouvé dans le panier: " + cartId);
        }

        List<CartItem> updatedItems = new ArrayList<>();
        
        for (CartItem item : cartItems) {
            // Appliquer les modifications
            if (prixUnitaire != null) {
                item.setPrixUnitaire(prixUnitaire);
            }
            
            if (pourcentage != null) {
                item.setRemisePourcentage(pourcentage);
                double remiseMontant = (item.getPrixUnitaire() * pourcentage) / 100;
                item.setRemiseMontant(remiseMontant);
            }
            
            if (quantity != null) {
                item.setQuantity(quantity);
            }
            
            // Calculer les nouveaux prix
            double prixApresRemise = item.getPrixUnitaire() - item.getRemiseMontant();
            item.setPrixApresRemise(prixApresRemise);
            item.setTotalItem(prixApresRemise * item.getQuantity());
            
            // Sauvegarder
            CartItem updatedItem = cartItemRepository.save(item);
            updatedItems.add(updatedItem);
        }
        
        return updatedItems;
    }
    
    // ... existing code ...

    
    public CartItem findById(Long id) {
        return cartItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("CartItem non trouvé"));
    }
    
    public void recalculerTotaux(CartItem item) {
        if (item.getRemisePourcentage() != null) {
            double reduction = (item.getPrixUnitaire() * item.getRemisePourcentage()) / 100;
            item.setRemiseMontant(reduction);
        }
        
        double prixApresRemise = item.getPrixUnitaire() - (item.getRemiseMontant() != null ? item.getRemiseMontant() : 0);
        item.setPrixApresRemise(prixApresRemise);
        item.setTotalItem(prixApresRemise * item.getQuantity());
        
        cartItemRepository.save(item);
    }
}