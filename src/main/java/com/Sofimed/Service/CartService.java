package com.Sofimed.Service;

import com.Sofimed.Dao.CartItemRepository;
import com.Sofimed.Dao.CartRepository;
import com.Sofimed.Dao.ProduitRepository;

import com.Sofimed.Model.Cart;
import com.Sofimed.Model.Cart.CartStatus;
import com.Sofimed.Model.CartItem;
import com.Sofimed.Model.Produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    
 // ... existing code ...
    public CartItem findItemInCart(Long cartId, Long itemId) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(itemId);
        if (itemOpt.isPresent() && itemOpt.get().getCart().getId().equals(cartId)) {
            return itemOpt.get();
        }
        return null;
    }
    // ... existing code ...

    public List<CartItem> getCartItems(Long cartId) {
        // Vérifier si le panier existe
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        // Récupérer tous les articles du panier
        return cartItemRepository.findByCartId(cartId);
    }
 
    public Cart getOrCreateActiveCart(Long clientId) {
        // Validation du clientId
        if (clientId == null) {
            throw new IllegalArgumentException("L'ID du client ne peut pas être null");
        }

        try {
            // Chercher un panier actif existant avec gestion des exceptions
            Cart activeCart = cartRepository.findByClientIdAndStatus(clientId, CartStatus.ACTIVE);
            
            // Si un panier actif existe, le retourner
            if (activeCart != null) {
                return activeCart;
            }
            
            // Création d'un nouveau panier avec validation
            Cart newCart = new Cart();
            newCart.setClientId(clientId);
            newCart.setStatus(CartStatus.ACTIVE);
            newCart.setCreatedAt(LocalDateTime.now());
          
            
            // Sauvegarder le nouveau panier avec gestion des exceptions
            try {
                return cartRepository.save(newCart);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la création du panier: " + e.getMessage(), e);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération/création du panier: " + e.getMessage(), e);
        }
    }
    public Cart getCart(Long cartId) {
        try {
            // Vérifier si l'ID du panier est valide
            if (cartId == null) {
                return null;
            }

            // Récupérer le panier depuis le repository
            Optional<Cart> cart = cartRepository.findById(cartId);
            
            // Retourner le panier s'il existe, sinon null
            return cart.orElse(null);
            
        } catch (Exception e) {
            // Logger l'erreur pour le debugging
            e.printStackTrace();
            return null;
        }
    }
    
    

    public CartItem addItemToCart(Long cartId, Long produitId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduit().getId().equals(produitId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return item;
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduit(produit);
            newItem.setQuantity(quantity);
            newItem.setPrix(produit.getPrix());
            cart.getItems().add(newItem);
            return newItem;
        }
    }
    
    public void removeItemFromCart(Long cartId, Long produitId) {
        Optional<CartItem> cartItem = cartItemRepository.findByCartIdAndProduitId(cartId, produitId);
        cartItem.ifPresent(cartItemRepository::delete);
    }

 // ... existing code ...

    public CartItem updateCartItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow();
        
        CartItem item = cart.getItems().stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow();
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public CartItem updateCartItemQuantity(Long cartId, Long produitId, Integer quantity) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartIdAndProduitId(cartId, produitId);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
            return cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("CartItem not found");
        }
    }


    // ... existing code ...

    // ... autres méthodes pour update et delete
}