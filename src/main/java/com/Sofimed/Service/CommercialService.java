package com.Sofimed.Service;




import com.Sofimed.Dao.CommercialRepository;
import com.Sofimed.Model.Cart;
import com.Sofimed.Model.CartItem;
import com.Sofimed.Model.Commercial;
import com.Sofimed.Model.Departement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;

@Service
public class CommercialService {

    @Autowired
    private CommercialRepository commercialRepository;
    
    private Departement findMajorityDepartment(Cart cart) {
        Map<Departement, Integer> departmentCount = new HashMap<>();
        
        for (CartItem item : cart.getItems()) {
            if (item.getProduit() != null && item.getProduit().getDepartement() != null) {
                departmentCount.merge(
                    item.getProduit().getDepartement(), 
                    item.getQuantity(), 
                    Integer::sum
                );
            }
        }
        
        return departmentCount.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RuntimeException("Aucun département trouvé dans le panier"));
    }

    // Méthode pour trouver un commercial disponible dans un département
    public Commercial findAvailableCommercialForDepartment(Departement departement) {
        return commercialRepository.findFirstByDepartement(departement)
            .orElseThrow(() -> new RuntimeException(
                "Aucun commercial disponible pour le département: " + departement.getNom()
            ));
    }

    // Méthode pour assigner un commercial à un panier
    public Commercial assignCommercialToCart(Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Le panier est vide ou invalide");
        }

        try {
            // Trouver le département majoritaire
            Departement majorityDepartment = findMajorityDepartment(cart);
            
            // Trouver un commercial disponible
            return findAvailableCommercialForDepartment(majorityDepartment);
            
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'assignation du commercial: " + e.getMessage());
        }
    }

    // Méthode pour vérifier la disponibilité d'un commercial
    public boolean isCommercialAvailable(Commercial commercial) {
        if (commercial == null) {
            return false;
        }
        
        // Ici vous pouvez ajouter votre logique de vérification
        // Par exemple, vérifier le nombre de devis en cours, etc.
        return true;
    }

    public Commercial findCommercialByCartProducts(Cart cart) {
        // Compter les occurrences de chaque département
        Map<Departement, Integer> departmentCount = new HashMap<>();
        
        cart.getItems().forEach(item -> {
            Departement dept = item.getProduit().getDepartement();
            departmentCount.put(dept, departmentCount.getOrDefault(dept, 0) + item.getQuantity());
        });

        // Trouver le département majoritaire
        Departement majorityDepartment = departmentCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);

        if (majorityDepartment == null) {
            return null;
        }

        // Trouver un commercial disponible pour ce département
        return commercialRepository.findFirstByDepartement(majorityDepartment)
            .orElse(null);
    }
}