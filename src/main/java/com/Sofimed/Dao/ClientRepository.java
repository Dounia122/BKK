package com.Sofimed.Dao;

import com.Sofimed.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUserId(Long userId);
    Optional<Client> findByClientCode(String clientCode);
    boolean existsByPhone(String phone);
    Optional<Client> findByUserEmail(String email);
    default Client findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Client avec ID " + id + " introuvable"));
    }
    
}