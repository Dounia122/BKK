package com.Sofimed.DTO;

import java.time.LocalDateTime;

import com.Sofimed.Model.Consultation;

public class ConsultationDTO {
    private Long id;
    private String subject;
    private String message;
    private String fileName;
    private ConsultationStatus status;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructeur par défaut
    public ConsultationDTO() {}

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Conversion depuis l'entité Consultation
    public static ConsultationDTO fromEntity(Consultation consultation) {
        ConsultationDTO dto = new ConsultationDTO();
        dto.setId(consultation.getId());
        dto.setSubject(consultation.getSubject());
        dto.setMessage(consultation.getMessage());
        dto.setFileName(consultation.getFileName());
        dto.setStatus(consultation.getStatus());
        dto.setUserName(consultation.getUser().getUsername());
        dto.setCreatedAt(consultation.getCreatedAt());
        dto.setUpdatedAt(consultation.getUpdatedAt());
        return dto;
    }
}
