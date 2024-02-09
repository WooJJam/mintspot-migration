package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.Chat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(Chat chat) {
        em.persist(chat);
    }
    public Chat findById(Long chatId) {
        return em.find(Chat.class, chatId);
    }

    public List<Chat> findAll() {
        return em.createQuery("select c from Chat c", Chat.class)
                .getResultList();
    }
}
