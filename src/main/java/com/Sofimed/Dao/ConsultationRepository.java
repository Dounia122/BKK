package com.Sofimed.Dao;

import com.Sofimed.Model.Consultation;
import com.Sofimed.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    // Trouver les consultations d'un utilisateur
    List<Consultation> findByUser(User user);

    // Trouver les consultations d'un utilisateur par ordre décroissant de création
    List<Consultation> findByUserOrderByCreatedAtDesc(User user);

    // Trouver toutes les consultations par ordre décroissant de création
    List<Consultation> findAllByOrderByCreatedAtDesc();
}
