package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.Bot;
import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.dto.chat.ChatDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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

        public List<Message> findUserMessage(Long chatId) {
            return em.createQuery(
                            "select m from Message m" +
                                    " join fetch m.chat c" +
                                    " where c.id =:chatId", Message.class)
                    .setParameter("chatId", chatId)
                    .getResultList();
        }
    }
