package com.Sofimed.Controller;

import com.Sofimed.Model.Message;
import com.Sofimed.Model.Devis;
import com.Sofimed.Service.MessageService;
import com.Sofimed.DTO.MessageDTO;
import com.Sofimed.Dao.DevisRepository;
import com.Sofimed.Exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
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
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private DevisRepository devisService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/devis/{devisId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByDevis(@PathVariable Long devisId) {
        List<Message> messages = messageService.findByDevisId(devisId);
        List<MessageDTO> dtos = messages.stream().map(MessageDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Envoyer un nouveau message et notifier via WebSocket
    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody Map<String, Object> messageRequest) {
        try {
            Long devisId = Long.parseLong(messageRequest.get("devisId").toString());
            String content = (String) messageRequest.get("content");
            Long senderId = Long.parseLong(messageRequest.get("senderId").toString());
            String senderName = (String) messageRequest.get("senderName");
            Long recipientId = Long.parseLong(messageRequest.get("recipientId").toString());

            Devis devis = devisService.findById(devisId)
                .orElseThrow(() -> new ResourceNotFoundException("Devis non trouvé avec l'ID: " + devisId));

            Message message = messageService.saveMessage(devis, senderId, senderName, recipientId, content);
            MessageDTO dto = MessageDTO.fromEntity(message);

            // Notifier les clients connectés au salon du devis via WebSocket
            messagingTemplate.convertAndSend("/topic/messages/" + devisId, dto);

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Marquer les messages comme lus
    @PutMapping("/devis/{devisId}/read")
    public ResponseEntity<Void> markMessagesAsRead(
            @PathVariable Long devisId,
            @RequestParam(required = false) Long recipientId) {

        try {
            if (recipientId != null) {
                // Marquer comme lus les messages envoyés à un destinataire spécifique
                messageService.markAsReadByRecipient(devisId, recipientId);
            } else {
                // Marquer tous les messages du devis comme lus
                messageService.markAllAsRead(devisId);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Compter les messages non lus
    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Long>> countUnreadMessages(
            @RequestParam Long userId,
            @RequestParam(required = false) String userType) {

        try {
            Map<String, Long> counts = messageService.countUnreadMessages(userId, userType);
            return ResponseEntity.ok(counts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}