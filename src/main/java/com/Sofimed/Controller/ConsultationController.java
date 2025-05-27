package com.Sofimed.Controller;

import com.Sofimed.DTO.ConsultationDTO;
import com.Sofimed.Model.Consultation;
import com.Sofimed.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@CrossOrigin(
	    origins = "http://localhost:3000",
	    allowedHeaders = "*",
	    allowCredentials = "true",
	    methods = {
	        RequestMethod.GET,
	        RequestMethod.POST,
	        RequestMethod.PUT,
	        RequestMethod.DELETE,
	        RequestMethod.OPTIONS
	    }
	)

public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<?> createConsultation(
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("username") String username) {
        try {
            Consultation consultation = consultationService.createConsultation(subject, message, file, userId, username);
            return ResponseEntity.ok(consultation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création de la consultation: " + e.getMessage());
        }
    }

    

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Resource file = consultationService.loadFileAsResource(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {
        List<ConsultationDTO> dtos = consultationService.getAllConsultations()
                .stream()
                .map(ConsultationDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }

}