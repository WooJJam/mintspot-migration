package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.Bot;
import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
                        "select uc from User u" +
                                " join u.chats uc" +
                                " where u.id = :userId", Chat.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Message> newestListUserMessage(Long chatId) {
        try {
            return em.createQuery(
                            "select distinct m from Message m" +
                                    " join fetch m.chat c" +
//                                    " join c.bot b" +
                                    " where c.id = :chatId" +
                                    " order by m.createdAt desc", Message.class)
                    .setParameter("chatId", chatId)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Bot> newestListBotMessage(Long chatId) {
        try {
            return em.createQuery(
                            "select distinct b from Bot b" +
                                    " join fetch b.chat c" +
//                                    " join c.bot b" +
                                    " where c.id = :chatId" +
                                    " order by b.createdAt desc", Bot.class)
                    .setParameter("chatId", chatId)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
