package com.Sofimed.Service;



import com.Sofimed.Model.Message;
import com.Sofimed.Model.Devis;
import com.Sofimed.Dao.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    public List<Message> findByDevisId(Long devisId) {
        return messageRepository.findByDevisIdOrderByTimestampAsc(devisId);
    }
    
    @Transactional
    public Message saveMessage(Devis devis, Long senderId, String senderName, Long recipientId, String content) {
        Message message = new Message();
        message.setDevis(devis);
        message.setSenderId(senderId);
        message.setSenderName(senderName);
        message.setRecipientId(recipientId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        
        return messageRepository.save(message);
    }
    
    @Transactional
    public void markAsReadByRecipient(Long devisId, Long recipientId) {
        messageRepository.markAsReadByRecipient(devisId, recipientId);
    }
    
    @Transactional
    public void markAllAsRead(Long devisId) {
        messageRepository.markAllAsRead(devisId);
    }
    
    public Map<String, Long> countUnreadMessages(Long userId, String userType) {
        Map<String, Long> result = new HashMap<>();
        
        if ("commercial".equalsIgnoreCase(userType)) {
            // Compter les messages non lus par devis pour un commercial
            List<Object[]> counts = messageRepository.countUnreadMessagesByDevisForCommercial(userId);
            for (Object[] count : counts) {
                Long devisId = (Long) count[0];
                Long unreadCount = (Long) count[1];
                result.put(devisId.toString(), unreadCount);
            }
        } else {
            // Compter les messages non lus par devis pour un client
            List<Object[]> counts = messageRepository.countUnreadMessagesByDevisForClient(userId);
            for (Object[] count : counts) {
                Long devisId = (Long) count[0];
                Long unreadCount = (Long) count[1];
                result.put(devisId.toString(), unreadCount);
            }
        }
        
        // Ajouter le total des messages non lus
        Long totalUnread = result.values().stream().mapToLong(Long::longValue).sum();
        result.put("total", totalUnread);
        
        return result;
    }
}