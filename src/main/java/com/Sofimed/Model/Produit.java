package com.Sofimed.Model;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "produits")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(unique = true)
    private String reference;
    
    @Column(unique = true)
    private String sku;
    
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;
    
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
    
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
    
    @Column(length = 2000)
    private String description;
    
    private Double prix;
    
    private String imageUrl;
    
    @Column(name = "fiche_technique")
    private String ficheTechnique;
    
    @Embedded
    private SpecificationsTechniques specs;
    
    @ElementCollection
    private List<String> caracteristiques;
    
    @CreationTimestamp
    private LocalDateTime dateCreation;
    
    @UpdateTimestamp
    private LocalDateTime dateModification;

    // Constructors
    public Produit() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getReference() {
        return reference;
    }

    public String getSku() {
        return sku;
    }

    public Marque getMarque() {
        return marque;
    }

    public Departement getDepartement() {
        return departement;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrix() {
        return prix;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFicheTechnique() {
        return ficheTechnique;
    }

    public SpecificationsTechniques getSpecs() {
        return specs;
    }

    public List<String> getCaracteristiques() {
        return caracteristiques;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFicheTechnique(String ficheTechnique) {
        this.ficheTechnique = ficheTechnique;
    }

    public void setSpecs(SpecificationsTechniques specs) {
        this.specs = specs;
    }

    public void setCaracteristiques(List<String> caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
}