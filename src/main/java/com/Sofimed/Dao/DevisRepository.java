package com.Sofimed.Dao;




import com.Sofimed.DTO.DevisStatus;
import com.Sofimed.Model.Commercial;
import com.Sofimed.Model.Devis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
    // Trouver tous les devis d'un commercial
    List<Devis> findByCommercial(Commercial commercial);
    
    @Query("SELECT d FROM Devis d JOIN d.cart c WHERE c.clientId = :clientId ORDER BY d.createdAt DESC")
    List<Devis> findByClientId(@Param("clientId") Long clientId);
    
    /**
     * Compte le nombre total de devis pour la génération de références
     */
    @Query("SELECT COUNT(d) FROM Devis d")
    long count();
    List<Devis> findByCommercialId(Long commercialId);
    List<Devis> findByCartClientId(Long clientId);
    
    @Query("SELECT MAX(CAST(SUBSTRING(d.reference, 9) AS Long)) FROM Devis d WHERE d.reference LIKE CONCAT('DEV-', :year, '-%')")
    Long findMaxSequenceForYear(@Param("year") String year);
    
    // Trouver les devis par statut
    List<Devis> findByStatus(DevisStatus status);
    
    // Trouver les devis d'un commercial par statut
    List<Devis> findByCommercialAndStatus(Commercial commercial, DevisStatus status);
    
    // Trouver les devis créés entre deux dates
    List<Devis> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Trouver les devis d'un commercial créés entre deux dates
    List<Devis> findByCommercialAndCreatedAtBetween(
        Commercial commercial, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
    
    // Compter le nombre de devis par statut
    long countByStatus(DevisStatus status);
    
    // Compter le nombre de devis par commercial et statut
    long countByCommercialAndStatus(Commercial commercial, DevisStatus status);
}