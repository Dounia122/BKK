package com.Sofimed.Controller;


import com.Sofimed.DTO.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {

    @MessageMapping("/chat/{devisId}")
    @SendTo("/topic/messages/{devisId}")
    public MessageDTO sendMessage(MessageDTO message) {
        // Ici, tu peux sauvegarder le message si besoin
        return message;
    }
}