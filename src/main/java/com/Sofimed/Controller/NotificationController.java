package com.Sofimed.Controller;


import com.Sofimed.DTO.MessageNotificationRequest;
import com.Sofimed.Dao.NotificationRepository;
import com.Sofimed.Model.Notification;
import com.Sofimed.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUser(@PathVariable Long userId) {
        // À adapter selon votre UserRepository/service
        User user = new User();
        user.setId(userId);
        return notificationRepository.findByUser(user);
    }

    @PostMapping("/")
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationRepository.save(notification);
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        Notification notif = notificationRepository.findById(id).orElseThrow();
        notif.setIsRead(true);
        return notificationRepository.save(notif);
    }
    
    @PostMapping("/message")
    public Notification notifyMessageSent(@RequestBody MessageNotificationRequest request) {
        Notification notif = new Notification();
        User user = new User();
        user.setId(request.getRecipientId()); // ID du client destinataire

        notif.setUser(user);
        notif.setTitle("Nouveau message reçu");
        notif.setMessage(request.getMessage());
        notif.setType("info");
        notif.setIsRead(false);
        notif.setLink(request.getLink()); // Lien optionnel vers la conversation

        return notificationRepository.save(notif);
    }

}