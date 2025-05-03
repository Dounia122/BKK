package com.Sofimed.Service;



import com.Sofimed.Dao.ClientRepository;
import com.Sofimed.Model.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public Client findByUserId(Long userId) {
        return clientRepository.findByUserId(userId);
            
    }

    
    public Client save(Client client) {
        return clientRepository.save(client);
    }
    
    public Client update(Client client) {
        if (!clientRepository.existsById(client.getId())) {
            throw new RuntimeException("Client non trouvé avec l'ID: " + client.getId());
        }
        return clientRepository.save(client);
    }
    
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
    
    public Client findById(Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));
    }
}