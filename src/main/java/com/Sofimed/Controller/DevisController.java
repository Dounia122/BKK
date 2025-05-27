package com.Sofimed.Controller;



import com.Sofimed.Model.Devis;
import com.Sofimed.Model.Cart;
import com.Sofimed.Model.CartItem;
import com.Sofimed.Model.Client;
import com.Sofimed.DTO.ClientDTO;
import com.Sofimed.DTO.DevisDTO;
import com.Sofimed.DTO.DevisStatus;
import com.Sofimed.DTO.dv;
import com.Sofimed.Dao.ClientRepository;
import com.Sofimed.Dao.DevisRepository;
import com.Sofimed.Exception.ResourceNotFoundException;
import com.Sofimed.Service.DevisService;
import com.Sofimed.Service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    private static final Logger log = LoggerFactory.getLogger(DevisController.class);

    @Autowired
    private DevisService devisService;

    @Autowired
    private CartService cartService;

    @Autowired
    private DevisRepository devisrepo;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("id/{devisId}")
    public ResponseEntity<Long> getCartId(@PathVariable Long devisId) {
        Devis devis = devisrepo.findById(devisId)
            .orElseThrow(() -> new ResourceNotFoundException("Devis not found"));
        Long cartId = devis.getCart().getId();
        System.out.println(cartId);
        return ResponseEntity.ok(cartId);
    }



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
            List<DevisDTO> devis = devisService.findDevisByClientId(userId);
            return ResponseEntity.ok(devis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    /*Obtenir tous les devis d'un commercial
    @GetMapping("/commercial/{commercialId}")
    public ResponseEntity<List<Devis>> getDevisByCommercial(@PathVariable Long commercialId) {
        try {
            List<Devis> devis = devisService.getDevisByCommercial(commercialId);
            return ResponseEntity.ok(devis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/
    

    @GetMapping("/commercial/{commercialId}") 
    public ResponseEntity<List<DevisDTO>> getDevisByCommercial(@PathVariable Long commercialId) { 
        try { 
            log.info("Récupération des devis pour le commercial ID: {}", commercialId); 
            List<DevisDTO> devis = devisService.getDevisByCommerciall(commercialId); 
            
            if (devis.isEmpty()) {
                log.info("Aucun devis trouvé pour le commercial ID: {}", commercialId);
                return ResponseEntity.ok(Collections.emptyList());
            }
            
            log.info("Nombre de devis trouvés: {}", devis.size()); 
            log.debug("Détails des devis: {}", devis);
            
            return ResponseEntity.ok(devis); 
        } catch (ResourceNotFoundException e) {
            log.warn("Commercial non trouvé avec l'ID: {}", commercialId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) { 
            log.error("Erreur lors de la récupération des devis pour le commercial {}: {}", commercialId, e.getMessage()); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<DevisDTO>> getDevisByClient(@PathVariable Long clientId) {
        try {
            List<DevisDTO> devis = devisService.findDevisByClientId(clientId);
            return ResponseEntity.ok(devis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.emptyList());
        }
    }
    
    
    
 // ... existing code ...

    @GetMapping("/{devisId}/client")
    public ResponseEntity<?> getClientInfoFromDevis(@PathVariable Long devisId) {
        try {
            log.info("Récupération des informations client pour le devis ID: {}", devisId);
            
            // Récupérer le devis
            Devis devis = devisrepo.getById(devisId);
            if (devis == null) {
                log.warn("Devis non trouvé avec l'ID: {}", devisId);
                return ResponseEntity.notFound().build();
            }

            // Récupérer le panier associé
            Cart cart = devis.getCart();
            if (cart == null) {
                log.warn("Panier non trouvé pour le devis ID: {}", devisId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Panier non trouvé pour ce devis");
            }

            // Récupérer l'ID du client
            Long clientId = cart.getClientId();
            if (clientId == null) {
                log.warn("Client non trouvé pour le devis ID: {}", devisId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client non trouvé pour ce devis");
            }

            // Récupérer les informations complètes du client
            Client client = clientRepository.findById(clientId).orElse(null);
            if (client == null) {
                log.warn("Client non trouvé avec l'ID: {}", clientId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client non trouvé");
            }

            // Convertir en ClientDTO
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(client.getId());
            clientDTO.setFirstName(client.getFirstName());
            clientDTO.setLastName(client.getLastName());
            clientDTO.setEmail(client.getEmail());
            clientDTO.setPhone(client.getPhone());
            clientDTO.setOrderCount(client.getOrderCount());
            
            clientDTO.setLastOrderDate(client.getLastOrderDate());
           

            log.info("Informations client récupérées avec succès pour le devis ID: {}", devisId);
            return ResponseEntity.ok(clientDTO);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des informations client pour le devis {}: {}", 
                     devisId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la récupération des informations client");
        }
    }
    
    
    
    
    @GetMapping("/{devisId}")
    public ResponseEntity<Devis> getDevisById(@PathVariable Long devisId) {
        try {
            Devis devis = devisrepo.findById(devisId)
                .orElse(null);
                
            if (devis == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(devis);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    /**
     * Récupère l'ID du panier associé à un devis
     */
    @GetMapping("/{devisId}/cart-id")
    public ResponseEntity<Long> getCartIdByDevisId(@PathVariable Long devisId) {
        try {
            Devis devis = devisrepo.findById(devisId)
                .orElse(null);
                
            if (devis == null) {
                return ResponseEntity.notFound().build();
            }
            
            Long cartId = devis.getCart().getId();
            return ResponseEntity.ok(cartId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Récupère les éléments du panier associé à un devis
     */
    @GetMapping("/{devisId}/items")
    public ResponseEntity<List<Map<String, Object>>> getDevisItems(@PathVariable Long devisId) {
        try {
            // Récupérer le devis
            Devis devis = devisrepo.findById(devisId)
                .orElse(null);
                
            if (devis == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Récupérer le panier associé au devis
            Cart cart = devis.getCart();
            if (cart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            // Récupérer les articles du panier
            List<CartItem> items = cartService.getCartItems(cart.getId());
            
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
                    
                    return details;
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(itemsWithDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère les éléments du panier associé à un devis
     */
    @GetMapping("/{devisId}/itemss")
    public ResponseEntity<List<Map<String, Object>>> getDevisItemss(@PathVariable Long devisId) {
        try {
            // Récupérer le devis
            Devis devis = devisrepo.findById(devisId)
                .orElse(null);
                
            if (devis == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Récupérer le panier associé au devis
            Cart cart = devis.getCart();
            if (cart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            // Récupérer les articles du panier
            List<CartItem> items = cartService.getCartItems(cart.getId());
            
            if (items == null || items.isEmpty()) {
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

                    // Champs supplémentaires liés aux prix et remises
                    details.put("prix", item.getPrix());
                    details.put("prixUnitaire", item.getPrixUnitaire());
                    details.put("remisePourcentage", item.getRemisePourcentage());
                    details.put("remiseMontant", item.getRemiseMontant());
                    details.put("prixApresRemise", item.getPrixApresRemise());
                    details.put("totalItem", item.getTotalItem());

                    return details;
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(itemsWithDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ... existing code ...
}