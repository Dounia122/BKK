package com.Sofimed.Service;


import com.Sofimed.Model.Devis;
import com.Sofimed.DTO.CommercialDTO;
import com.Sofimed.DTO.DevisDTO;

import com.Sofimed.DTO.DevisStatus;
import com.Sofimed.Dao.DevisRepository;
import com.Sofimed.Exception.ResourceNotFoundException;
import com.Sofimed.Model.Cart;
import com.Sofimed.Model.Commercial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;
    
    @Autowired
    private CommercialService commercialService;
    
    
    public List<Devis> getDevisByCommercial(Long commercialId) {
        return devisRepository.findByCommercialId(commercialId);
    }

    public List<Devis> getDevisByClient(Long clientId) {
        return devisRepository.findByCartClientId(clientId);
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
  
 // Trouver tous les devis d'un client
    public List<DevisDTO> findByClientId(Long clientId) {
        List<Devis> devisList = devisRepository.findByClientId(clientId);
        return devisList.stream()
            .map(devis -> {
                DevisDTO devisDTO = new DevisDTO();
                devisDTO.setId(devis.getId());
                devisDTO.setReference(devis.getReference());
                
                devisDTO.setStatus(devis.getStatus().toString());
                devisDTO.setCreatedAt(devis.getCreatedAt());
                
                devisDTO.setPaymentMethod(devis.getPaymentMethod());
                devisDTO.setCommentaire(devis.getCommentaire());
                
                if (devis.getCommercial() != null) {
                    Commercial commercial = devis.getCommercial();
                    CommercialDTO commercialDTO = new CommercialDTO(
                        commercial.getId(),
                        commercial.getFirstName(),
                        commercial.getLastName(),
                        commercial.getEmail(),
                        commercial.getPhone(),
                        commercial.getEmployeeCode()
                    );
                    devisDTO.setCommercial(commercialDTO);
                }
                
                // Par défaut à 0, à implémenter selon votre logique de messages
                devisDTO.setUnreadMessages(0);
                
                return devisDTO;
            })
            .collect(Collectors.toList());
    }
    
    // Trouver un devis par son ID
    public DevisDTO findById(Long id) {
    	Devis devis;

    	try {
    	    devis = devisRepository.findById(id).get(); // Peut lancer NoSuchElementException
    	} catch (NoSuchElementException e) {
    	    throw new ResourceNotFoundException(
    	        "Devis introuvable - ID: " + id + " n'existe pas dans la base de données", e
    	    );
    	}

        
        DevisDTO devisDTO = new DevisDTO();
        devisDTO.setId(devis.getId());
        devisDTO.setReference(devis.getReference());
        
        devisDTO.setStatus(devis.getStatus().toString());
        devisDTO.setCreatedAt(devis.getCreatedAt());
       
        devisDTO.setPaymentMethod(devis.getPaymentMethod());
        devisDTO.setCommentaire(devis.getCommentaire());
        
        if (devis.getCommercial() != null) {
            Commercial commercial = devis.getCommercial();
            CommercialDTO commercialDTO = new CommercialDTO(
                commercial.getId(),
                commercial.getFirstName(),
                commercial.getLastName(),
                commercial.getEmail(),
                commercial.getPhone(),
                commercial.getEmployeeCode()
            );
            devisDTO.setCommercial(commercialDTO);
        }
        
        // Par défaut à 0, à implémenter selon votre logique de messages
        devisDTO.setUnreadMessages(0);
        
        return devisDTO;
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



    public Devis updateDevisStatus(Long id, DevisStatus newStatus) {
        Devis devis = devisRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Devis non trouvé"));
        
        devis.setStatus(newStatus);
        return devisRepository.save(devis);
    }
}