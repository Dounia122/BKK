package com.Sofimed.Controller;



import com.Sofimed.Model.Devis;
import com.Sofimed.Model.Cart;
import com.Sofimed.DTO.DevisDTO;
import com.Sofimed.DTO.DevisStatus;
import com.Sofimed.Exception.ResourceNotFoundException;
import com.Sofimed.Service.DevisService;
import com.Sofimed.Service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devis")
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
public class DevisController {

    @Autowired
    private DevisService devisService;

    @Autowired
    private CartService cartService;

 // Créer un nouveau devis
    @PostMapping("/create")
    public ResponseEntity<Devis> createDevis(
            @RequestBody Map<String, Object> requestBody) {
        try {
            Long cartId = Long.parseLong(requestBody.get("cartId").toString());
            String paymentMethod = (String) requestBody.get("paymentMethod");
            String commentaire = (String) requestBody.get("commentaire");

            Cart cart = cartService.getCart(cartId);
            if (cart == null) {
                return ResponseEntity.notFound().build();
            }
            
            Devis newDevis = devisService.createDevis(cart, paymentMethod, commentaire);
            return ResponseEntity.ok(newDevis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
 // Récupérer tous les devis d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DevisDTO>> getDevisByUserId(@PathVariable Long userId) {
        try {
            List<DevisDTO> devis = devisService.findByClientId(userId);
            return ResponseEntity.ok(devis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Récupérer un devis par son ID
    @GetMapping("/{id}")
    public ResponseEntity<DevisDTO> getDevisById(@PathVariable Long id) {
        try {
            DevisDTO devis = devisService.findById(id);
            return ResponseEntity.ok(devis);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

  

    // Mettre à jour le statut d'un devis
    @PutMapping("/{id}/status")
    public ResponseEntity<Devis> updateDevisStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        try {
            DevisStatus newStatus = DevisStatus.valueOf(statusUpdate.get("status"));
            Devis updatedDevis = devisService.updateDevisStatus(id, newStatus);
            return ResponseEntity.ok(updatedDevis);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtenir tous les devis d'un commercial
    @GetMapping("/commercial/{commercialId}")
    public ResponseEntity<List<Devis>> getDevisByCommercial(@PathVariable Long commercialId) {
        try {
            List<Devis> devis = devisService.getDevisByCommercial(commercialId);
            return ResponseEntity.ok(devis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtenir tous les devis d'un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Devis>> getDevisByClient(@PathVariable Long clientId) {
        try {
            List<Devis> devis = devisService.getDevisByClient(clientId);
            return ResponseEntity.ok(devis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}