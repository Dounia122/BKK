package com.Sofimed.Controller;

import com.Sofimed.Dao.CategorieRepository;
import com.Sofimed.Dao.MarqueRepository;
import com.Sofimed.Dao.ProduitRepository;
import com.Sofimed.Model.Categorie;
import com.Sofimed.Model.Marque;
import com.Sofimed.Model.Produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", 
             allowedHeaders = "*",
             exposedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private MarqueRepository marqueRepository;

    @GetMapping("/produits/{id}")
    public ResponseEntity<?> getProduitById(@PathVariable Long id) {
        try {
            Optional<Produit> produit = produitRepository.findById(id);
            if (produit.isPresent()) {
                return ResponseEntity.ok(produit.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Produit non trouvé avec l'ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erreur lors de la récupération du produit: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/produits")
    public ResponseEntity<Page<Produit>> getAllProduits(
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Long marqueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Produit> produits;

            if (categorieId != null && marqueId != null) {
                produits = produitRepository.findByCategorieIdAndMarqueId(categorieId, marqueId, pageable);
            } else if (categorieId != null) {
                produits = produitRepository.findByCategorieId(categorieId, pageable);
            } else if (marqueId != null) {
                produits = produitRepository.findByMarqueId(marqueId, pageable);
            } else {
                produits = produitRepository.findAll(pageable);
            }

            if (produits.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(produits);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> categories = categorieRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/marques")
    public ResponseEntity<List<Marque>> getAllMarques() {
        List<Marque> marques = marqueRepository.findAll();
        return ResponseEntity.ok(marques);
    }
}
