package com.Sofimed.Controller;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Sofimed.DTO.CartItemUpdateRequest;
import com.Sofimed.Dao.CartItemRepository;
import com.Sofimed.Exception.ResourceNotFoundException;
import com.Sofimed.Model.CartItem;
import com.Sofimed.Service.CartItemService;



@RestController
@RequestMapping("/api/cart-items")
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
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    
 
    @Autowired
    private CartItemRepository cartItemRepository;
    
  
 // ... existing code ...
    @PutMapping("/update-by-cart-product")
    public ResponseEntity<?> updateByCartAndProductt(
        @RequestParam Long cartId,
        @RequestParam Long produitId,
        @RequestParam Double prixUnitaire,
        @RequestParam Double remisePourcentage
    ) {
        try {
            cartItemService.updateCartItemDiscount(cartId, produitId, prixUnitaire, remisePourcentage);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            // Log l'erreur si nécessaire
            return ResponseEntity.notFound().build(); // Ou ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Log l'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de l'article du panier.");
        }
    }
    // ... existing code ...


    @GetMapping("/find-item")
    public ResponseEntity<Long> findItemId(
        @RequestParam Long produitId,
        @RequestParam Long cartId
    ) {
        CartItem cartItem = cartItemRepository.findByProduitIdAndCartId(produitId, cartId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Item non trouvé pour produitId: " + produitId + " et cartId: " + cartId
            ));

        return ResponseEntity.ok(cartItem.getId());
    }

    

    @PostMapping("/update-price-discount")
    public ResponseEntity<?> updatePriceAndDiscount(
            @RequestParam Long cartId,
            @RequestParam Long produitId,
            @RequestParam Double prixUnitaire,
            @RequestParam Double remisePourcentage) {
        
        try {
            CartItem updatedItem = cartItemService.updateCartItem(
                cartId, produitId, prixUnitaire, remisePourcentage
            );
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating cart item: " + e.getMessage());
        }
    }
    
    @PutMapping("/{cartItemId}/update")
    public ResponseEntity<CartItem> updateCartItem(
        @PathVariable Long cartItemId,
        @RequestBody CartItemUpdateRequest request
    ) {
        CartItem updated = cartItemService.updateCartItem(
            cartItemId,
            request.getPrixUnitaire(),
            request.getRemisePourcentage(),
            request.getQuantity()
        );
        return ResponseEntity.ok(updated);
    }


   

    
 // ... existing code ...
    @PostMapping("/apply-discount")
    public ResponseEntity<List<CartItem>> applyDiscountToCartItems(
        @RequestParam Long cartId,
        @RequestParam(required = false) Double pourcentage,
        @RequestParam(required = false) Double prixUnitaire,
        @RequestParam(required = false) Integer quantity
    ) {
        List<CartItem> items = cartItemService.applyDiscountToCartItems(cartId, pourcentage, prixUnitaire, quantity);
        return ResponseEntity.ok(items);
    }
    // ... existing code ...
  
    
    @GetMapping("/{itemId}/details")
    public ResponseEntity<Map<String, Object>> getItemDetails(@PathVariable Long itemId) {
        CartItem item = cartItemService.findById(itemId);
        
        Map<String, Object> details = new HashMap<>();
        details.put("prixUnitaire", item.getPrixUnitaire());
        details.put("quantite", item.getQuantity());
        details.put("remisePourcentage", item.getRemisePourcentage());
        details.put("remiseMontant", item.getRemiseMontant());
        details.put("prixApresRemise", item.getPrixApresRemise());
        details.put("totalItem", item.getTotalItem());
        
        return ResponseEntity.ok(details);
    }
}