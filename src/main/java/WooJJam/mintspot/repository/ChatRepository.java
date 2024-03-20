package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.chat.Chat;
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

    public List<Chat> listChats(Long userId) {
        return em.createQuery(
                        "select c from Chat c" +
                                " join fetch c.user cu" +
                                " where cu.id = :userId", Chat.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
