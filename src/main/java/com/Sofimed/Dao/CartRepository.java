package com.Sofimed.Dao;


import com.Sofimed.Model.Cart;
import com.Sofimed.Model.Cart.CartStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // ... existing code ...
    
   
    
    
    Cart findByClientIdAndStatus(Long clientId, Cart.CartStatus status);
    List<Cart> findByClientId(Long clientId);
    // ... existing code ...
}