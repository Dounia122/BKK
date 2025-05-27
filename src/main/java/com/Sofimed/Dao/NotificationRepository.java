package com.Sofimed.Dao;



import com.Sofimed.Model.Notification;
import com.Sofimed.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsReadFalse(User user);
    List<Notification> findByUser(User user);
    
   
}