package com.Sofimed.Model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationsTechniques {
    private String entreeAir;
    private String debitMaximum;
    private String entreeLiquide;
    private String sortieLiquide;
    private String pressionMaximale;
    private String diametreSolideMax;
    private String typeConnexion;
    private String deplacement;
    private String hauteurAspirationMax;
    private String detailsConstruction;
    private String detailsFonctionnement;
    private String noteCycle;
}