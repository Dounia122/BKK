package com.Sofimed.Dao;



import com.Sofimed.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findByDevisIdOrderByTimestampAsc(Long devisId);
    
    @Modifying
    @Query("UPDATE Message m SET m.read = true WHERE m.devis.id = :devisId AND m.recipientId = :recipientId AND m.read = false")
    void markAsReadByRecipient(Long devisId, Long recipientId);
    
    @Modifying
    @Query("UPDATE Message m SET m.read = true WHERE m.devis.id = :devisId AND m.read = false")
    void markAllAsRead(Long devisId);
    
    @Query("SELECT m.devis.id, COUNT(m) FROM Message m WHERE m.recipientId = :commercialId AND m.read = false GROUP BY m.devis.id")
    List<Object[]> countUnreadMessagesByDevisForCommercial(Long commercialId);
    
    @Query("SELECT m.devis.id, COUNT(m) FROM Message m WHERE m.recipientId = :clientId AND m.read = false GROUP BY m.devis.id")
    List<Object[]> countUnreadMessagesByDevisForClient(Long clientId);
}