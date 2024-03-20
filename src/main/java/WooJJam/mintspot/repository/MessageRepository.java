package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveMessage(Chat chat, String content) {
        Message message = new Message();
        message.createMessage(chat, content);
        em.persist(message);
    }
}
