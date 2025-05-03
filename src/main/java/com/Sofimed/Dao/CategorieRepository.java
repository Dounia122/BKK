package com.Sofimed.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Sofimed.Model.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    List<Categorie> findByOrderByNomAsc();
    Optional<Categorie> findByNom(String nom);
}