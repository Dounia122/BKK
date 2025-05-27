package com.Sofimed.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sofimed.Model.Commercial;
import com.Sofimed.Service.CommercialService;

@RestController
@RequestMapping("/api/commercials")
public class CommercialController {
    @Autowired
    private CommercialService commercialService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCommercialByUserId(@PathVariable Long userId) {
        try {
            Optional<Commercial> commercial = commercialService.findByUserId(userId);
            if (commercial == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(commercial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la récupération du commercial: " + e.getMessage());
        }
    }
}