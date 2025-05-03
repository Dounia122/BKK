package com.Sofimed.Controller;



import com.Sofimed.DTO.CartItemRequest;
import com.Sofimed.Dao.CartRepository;
import com.Sofimed.Dao.ClientRepository;
import com.Sofimed.Model.Cart;
import com.Sofimed.Model.CartItem;
import com.Sofimed.Model.Client;

import com.Sofimed.Service.CartService;
import com.Sofimed.Service.ClientService;
import com.Sofimed.Service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/carts")
@CrossOrigin(
    origins = "http://localhost:3000",
    allowedHeaders = "*",
    allowCredentials = "true",
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    }
)
public class CartController {
   

    
    @Autowired
    private CartService cartService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userservice;
    
    @Autowired
    private ClientRepository clientrepo;
    
    @Autowired
    private CartRepository cartrepo;

    @GetMapping("/client/{clientId}/active")
    public ResponseEntity<Cart> getActiveCart(@PathVariable Long clientId) {
        Cart cart = cartService.getOrCreateActiveCart(clientId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItem> addItemToCart(
            @PathVariable Long cartId,
            @RequestBody CartItemRequest request) {
        CartItem item = cartService.addItemToCart(
            cartId, 
            request.getProduitId(), 
            request.getQuantity()
        );
        return ResponseEntity.ok(item);
    }


    
 // ... existing code ...

    @GetMapping("/current/{userId}")
    public ResponseEntity<Long> getCurrentCart(@PathVariable Long userId) {
        try {
            // Récupérer directement le client avec l'ID utilisateur
            Client client = clientrepo.findByUserId(userId);
            if (client == null) {
                return ResponseEntity.status(404).body(null);
            }

            // Récupérer ou créer le panier actif pour ce client
            Cart activeCart = cartService.getOrCreateActiveCart(client.getId());
            Long id=activeCart.getId();
            if (activeCart == null) {
                return ResponseEntity.status(500).build();
            }

            return ResponseEntity.ok(id);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/{cartId}/itemss")
    public ResponseEntity<List<Map<String, Object>>> getCartItems(@PathVariable Long cartId) {
        try {
            // Vérifier si le panier existe
            Cart cart = cartService.getCart(cartId);
            if (cart == null) {
                return ResponseEntity.notFound().build();
            }

            // Récupérer les articles du panier
            List<CartItem> items = cartService.getCartItems(cartId);
            
            if (items == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            // Transformer les CartItem en Map contenant toutes les informations nécessaires
            List<Map<String, Object>> itemsWithDetails = items.stream()
                .map(item -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("id", item.getProduit().getId());
                    details.put("nom", item.getProduit().getNom());
                    details.put("reference", item.getProduit().getReference());
                    details.put("imageUrl", item.getProduit().getImageUrl());
                    details.put("categorie", item.getProduit().getCategorie());
                    details.put("quantity", item.getQuantity());
                    details.put("prix", item.getPrix());
                    return details;
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(itemsWithDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    @PostMapping("/{cartId}/items/{produitId}/update")
    public ResponseEntity<CartItem> updateQuantity(
            @PathVariable Long cartId,
            @PathVariable Long produitId,
            @RequestBody Map<String, Integer> request) {
        
        try {
            CartItem item = cartService.updateCartItemQuantity(
                cartId,
                produitId,
                request.get("quantity")
            );
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @PostMapping("/{cartId}/items/{produitId}/remove")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long cartId,
            @PathVariable Long produitId) {

        try {
            cartService.removeItemFromCart(cartId, produitId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    

    // ... existing code ...
}