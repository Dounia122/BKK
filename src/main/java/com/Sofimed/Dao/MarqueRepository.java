package com.Sofimed.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Sofimed.Model.Marque;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {
    List<Marque> findByOrderByNomAsc();
    Optional<Marque> findByNom(String nom);
}