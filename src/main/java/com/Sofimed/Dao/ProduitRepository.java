package com.Sofimed.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Sofimed.Model.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    
 /*   @Query("SELECT p FROM Produit p LEFT JOIN FETCH p.categorie LEFT JOIN FETCH p.marque")
    List<Produit> findAllWithRelations();
    
    @Query(value = "SELECT DISTINCT p FROM Produit p LEFT JOIN FETCH p.categorie LEFT JOIN FETCH p.marque",
           countQuery = "SELECT COUNT(DISTINCT p) FROM Produit p")
    Page<Produit> findAllWithRelations(Pageable pageable);
    
    @Query(value = "SELECT DISTINCT p FROM Produit p LEFT JOIN FETCH p.categorie LEFT JOIN FETCH p.marque WHERE p.categorie.id = :categorieId",
           countQuery = "SELECT COUNT(DISTINCT p) FROM Produit p WHERE p.categorie.id = :categorieId")
    Page<Produit> findByCategorieId(@Param("categorieId") Long categorieId, Pageable pageable);
    
    @Query(value = "SELECT DISTINCT p FROM Produit p LEFT JOIN FETCH p.categorie LEFT JOIN FETCH p.marque WHERE p.marque.id = :marqueId",
           countQuery = "SELECT COUNT(DISTINCT p) FROM Produit p WHERE p.marque.id = :marqueId")
    Page<Produit> findByMarqueId(@Param("marqueId") Long marqueId, Pageable pageable);
    
    @Query(value = "SELECT DISTINCT p FROM Produit p LEFT JOIN FETCH p.categorie LEFT JOIN FETCH p.marque WHERE p.categorie.id = :categorieId AND p.marque.id = :marqueId",
           countQuery = "SELECT COUNT(DISTINCT p) FROM Produit p WHERE p.categorie.id = :categorieId AND p.marque.id = :marqueId")
    Page<Produit> findByCategorieIdAndMarqueId(
        @Param("categorieId") Long categorieId,
        @Param("marqueId") Long marqueId,
        Pageable pageable
    );*/
	


	    Page<Produit> findAll(Pageable pageable);

	    Page<Produit> findByCategorieId(Long categorieId, Pageable pageable);

	    Page<Produit> findByMarqueId(Long marqueId, Pageable pageable);

	    Page<Produit> findByCategorieIdAndMarqueId(Long categorieId, Long marqueId, Pageable pageable);
	}


