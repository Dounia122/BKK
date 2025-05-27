package com.Sofimed.DTO;

import java.time.LocalDateTime;

import com.Sofimed.Model.Message;

public class MessageDTO {
    private Long id;
    private String content;
    private Long senderId;
    private String senderName;
    private Long recipientId;
    private Boolean read;
    private Long devisId;
    private LocalDateTime timestamp;

    public static MessageDTO fromEntity(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSenderId(message.getSenderId());
        dto.setSenderName(message.getSenderName());
        dto.setRecipientId(message.getRecipientId());
        dto.setRead(message.isRead());
        dto.setDevisId(message.getDevis().getId());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
 // ... existing code ...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Long getDevisId() {
        return devisId;
    }

    public void setDevisId(Long devisId) {
        this.devisId = devisId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
// ... existing code ...
    // getters et setters...
}