package com.Sofimed.Service;


import com.Sofimed.Model.Devis;
import com.Sofimed.DTO.ClientDTO;
import com.Sofimed.DTO.CommercialDTO;
import com.Sofimed.DTO.DevisDTO;

import com.Sofimed.DTO.DevisStatus;
import com.Sofimed.DTO.dv;
import com.Sofimed.Dao.CartRepository;
import com.Sofimed.Dao.ClientRepository;
import com.Sofimed.Dao.DevisRepository;
import com.Sofimed.Exception.ResourceNotFoundException;
import com.Sofimed.Model.Cart;
import com.Sofimed.Model.Client;
import com.Sofimed.Model.Commercial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;
    
    @Autowired
    private CommercialService commercialService;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ClientRepository Clientrepo;
    
    
    public List<Devis> getDevisByCommercial(Long commercialId) {
        return devisRepository.findByCommercialId(commercialId);
    }

    public List<Devis> getDevisByClient(Long clientId) {
        return devisRepository.findByCartClientId(clientId);
    }
    
    
    public List<DevisDTO> findDevisByClientId(Long clientId) {
        try {
            // Récupérer tous les paniers du client
            List<Cart> clientCarts = cartRepository.findByClientId(clientId);
            
            if (clientCarts.isEmpty()) {
                return new ArrayList<>();
            }
            
            // Récupérer et formater les devis avec les détails du commercial
            return clientCarts.stream()
                .flatMap(cart -> devisRepository.findByCartId(cart.getId()).stream())
                .map(this::convertToDTO)
                .sorted((d1, d2) -> d2.getCreatedAt().compareTo(d1.getCreatedAt()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des devis: " + e.getMessage());
        }
    }
    
    private DevisDTO convertToDTO(Devis devis) {
        return new DevisDTO(
            devis.getId(),
            devis.getReference(),
            devis.getStatus().name(),
            devis.getCreatedAt(),
            devis.getPaymentMethod(),
            devis.getCommentaire(),
            convertToCommercialDTO(devis.getCommercial()),
            calculateUnreadMessages(devis),
            devis.getCommercial() != null ? devis.getCommercial().getEmail() : null
        );
    }
    private CommercialDTO convertToCommercialDTO(Commercial commercial) {
        if (commercial == null) {
            return null;
        }
        return new CommercialDTO(
            commercial.getId(),
            commercial.getFirstName(),
            commercial.getLastName(),
            commercial.getEmail(),
            commercial.getPhone(),
            commercial.getEmployeeCode()
        );
    }
    
    private Integer calculateUnreadMessages(Devis devis) {
        // This method should be implemented to count unread messages
        // For now, returning 0 as default
        return 0;
    }
    
    

  /*  public Devis createDevis(Cart cart, String paymentMethod, String commentaire) {
        // Trouver le commercial approprié basé sur les produits du panier
        Commercial commercial = commercialService.findCommercialByCartProducts(cart);
        
        Devis devis = new Devis();
        devis.setCart(cart);
        devis.setCommercial(commercial);
        devis.setPaymentMethod(paymentMethod);
        devis.setCommentaire(commentaire);
        devis.setCreatedAt(LocalDateTime.now());
        devis.setStatus(DevisStatus.EN_ATTENTE);
        
        return devisRepository.save(devis);
    }
    public Devis getDevisById(Long id) {
        return devisRepository.findById(id).orElse(null);
    }*/
    
    private String generateDevisReference() {
        // Obtenir l'année courante
        String year = String.valueOf(LocalDateTime.now().getYear());
        
        // Obtenir le dernier numéro de séquence
        Long lastSequence = devisRepository.findMaxSequenceForYear(year);
        int nextSequence = (lastSequence != null ? lastSequence.intValue() + 1 : 1);
        
        // Formater le numéro de séquence sur 3 chiffres
        String sequenceFormatted = String.format("%03d", nextSequence);
        
        // Générer la référence au format DEV-YYYY-XXX
        return String.format("DEV-%s-%s", year, sequenceFormatted);
    }
  
 
  
        
        
        

   
    public Devis createDevis(Cart cart, String paymentMethod, String commentaire) {
        // Trouver le commercial approprié basé sur les produits du panier
        Commercial commercial = commercialService.findCommercialByCartProducts(cart);
        
        Devis devis = new Devis();
        devis.setCart(cart);
        devis.setCommercial(commercial);
        devis.setPaymentMethod(paymentMethod);
        devis.setCommentaire(commentaire);
        devis.setCreatedAt(LocalDateTime.now());
        devis.setStatus(DevisStatus.EN_ATTENTE);
        
        // Générer et définir la référence unique
        devis.setReference(generateDevisReference());
        
        // Calculer le total du devis
      
        
        return devisRepository.save(devis);
    }
    
    
    private ClientDTO convertToClientDTO(Client client) {
        if (client == null) return null;
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setLastName(client.getLastName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setOrderCount(client.getOrderCount());
        clientDTO.setProfileStatus(determineProfileStatus(client));
        clientDTO.setLastOrderDate(client.getLastOrderDate());
        
        
        return clientDTO;
    }

    private String determineProfileStatus(Client client) {
        if (client.getOrderCount() == 0) return "NOUVEAU";
        if (client.getOrderCount() > 10) return "FIDÈLE";
        if (client.getOrderCount() > 5) return "RÉGULIER";
        return "OCCASIONNEL";
    }
    
    public List<Devis> getDevisByCommercialL(Long commercialId) {
        List<Devis> devis = devisRepository.findByCommercialId(commercialId);
        return devis;
    }

   

    
    public List<DevisDTO> getDevisByCommerciall(Long commercialId) {
        List<Devis> devis = devisRepository.findByCommercialId(commercialId);
        return devis.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    
    public List<Devis> getDevisByCommercialS(Long commercialId) {
        List<Devis> devisList = devisRepository.findByCommercialId(commercialId);
        
        // Pour chaque devis, charger explicitement le client via le cart
        for (Devis devis : devisList) {
            if (devis.getCart() != null) {
                Client client = Clientrepo.findById(devis.getCart().getClientId())
                    .orElse(null);
                if (client != null) {
                    devis.getCart().setClientId(client.getId());
                }
            }
        }
        
        return devisList;
    }
    public dv getDevis(Long devisId) {
        Devis devis = devisRepository.findById(devisId)
            .orElseThrow(() -> new ResourceNotFoundException("Devis not found"));
        return new dv(devis.getId(), devis.getCart().getId(), devis.getReference(),
            devis.getPaymentMethod(), devis.getCommentaire(), devis.getCreatedAt(),
            devis.getStatus(), devis.getTotale()); // Supprimez le deuxième devis.getCart().getId()
    }
    


    public Devis updateDevisStatus(Long id, DevisStatus newStatus) {
        Devis devis = devisRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Devis non trouvé"));
        
        devis.setStatus(newStatus);
        return devisRepository.save(devis);
    }
}