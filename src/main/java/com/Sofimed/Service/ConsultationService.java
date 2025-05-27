package com.Sofimed.Service;

import com.Sofimed.Dao.ClientRepository;
import com.Sofimed.Dao.ConsultationRepository;
import com.Sofimed.Dao.UserRepository;
import com.Sofimed.Model.Client;
import com.Sofimed.Model.Consultation;
import com.Sofimed.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    private final Path fileStorageLocation;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    public ConsultationService() throws IOException {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

    public Consultation createConsultation(String subject, String message, MultipartFile file, Long userId, String username) throws Exception {
        // Vérification utilisateur existant
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("Utilisateur introuvable avec l'id : " + userId);
        }
        User user = optionalUser.get();

        // Vérifier que le username correspond à l'utilisateur
        if (!user.getUsername().equals(username)) {
            throw new Exception("Le nom d'utilisateur ne correspond pas à l'id fourni.");
        }

        // 🔥 Récupérer le client lié à l'utilisateur
        Client client = clientRepository.findByUser(user)
            .orElseThrow(() -> new Exception("Client introuvable pour l'utilisateur : " + username));

        Consultation consultation = new Consultation();
        consultation.setSubject(subject);
        consultation.setMessage(message);
        consultation.setUser(user);
        consultation.setClient(client); // 💥 Clé pour corriger l'erreur SQL

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            consultation.setFileName(fileName);
            consultation.setFilePath(targetLocation.toString());
        }

        return consultationRepository.save(consultation);
    }
    

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public List<Consultation> getConsultationsByUser(Long userId, String username) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("Utilisateur introuvable avec l'id : " + userId);
        }
        User user = optionalUser.get();
        if (!user.getUsername().equals(username)) {
            throw new Exception("Le nom d'utilisateur ne correspond pas à l'id fourni.");
        }

        return consultationRepository.findByUser(user);
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Fichier non trouvé : " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new Exception("Erreur lors du chargement du fichier : " + fileName, e);
        }
    }
}
