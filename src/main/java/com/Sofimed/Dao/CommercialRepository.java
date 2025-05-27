package com.Sofimed.Dao;

import com.Sofimed.Model.Commercial;
import com.Sofimed.Model.Departement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;





@Repository
public interface CommercialRepository extends JpaRepository<Commercial, Long> {
    
    @Query("SELECT c FROM Commercial c WHERE c.departement.nom = :departementNom")
    Commercial findFirstByDepartementNom(@Param("departementNom") String departementNom);
    
    // Trouver un commercial par département
    Optional<Commercial> findFirstByDepartement(Departement departement);
    
    // Trouver tous les commerciaux d'un département
    List<Commercial> findAllByDepartement(Departement departement);
    
    // Trouver un commercial par son code employé
    Optional<Commercial> findByEmployeeCode(String employeeCode);
    
    // Trouver un commercial par son userId
    Optional<Commercial> findByUserId(Long userId);
    
    // Vérifier si un code employé existe déjà
    boolean existsByEmployeeCode(String employeeCode);


    
}