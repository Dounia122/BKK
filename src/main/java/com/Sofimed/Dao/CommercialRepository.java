package com.Sofimed.Dao;



import com.Sofimed.Model.Commercial;
import com.Sofimed.Model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CommercialRepository extends JpaRepository<Commercial, Long> {
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