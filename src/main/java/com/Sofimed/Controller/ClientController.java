package com.Sofimed.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sofimed.Model.Client;
import com.Sofimed.Service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getClientByUserId(@PathVariable Long userId) {
        try {
            Client client = clientService.findByUserId(userId);
            if (client == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la récupération du client: " + e.getMessage());
        }
    }
}