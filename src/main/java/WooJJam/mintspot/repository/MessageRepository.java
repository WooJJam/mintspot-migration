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

    public Message saveMessage(Chat chat, String content) {
        Message message = new Message();
        message.createMessage(chat, content);
        em.persist(message);
        return message;
    }

    public List<Message> listMessage(Long chatId) {
        return em.createQuery(
                "select cm from Chat c" +
                        " join c.messages cm" +
                        " where c.id =: chatId", Message.class)
                .setParameter("chatId", chatId)
                .getResultList();
    }
}
