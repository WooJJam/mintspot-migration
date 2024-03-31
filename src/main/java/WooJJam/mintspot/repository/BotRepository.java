package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.Bot;
import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BotRepository {

    @PersistenceContext
    private EntityManager em;

    public Bot saveBotMessage(Chat chat, String content) {
        Bot bot = new Bot();
        bot.createBot(chat, content);
        em.persist(bot);
        return bot;
    }

    public List<Bot> findBotMessage (Long chatId, int offset, int limit) {
        return em.createQuery(
                        "select b from Bot b" +
                                " join fetch b.chat c" +
                                " where c.id =:chatId", Bot.class)
                .setParameter("chatId", chatId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
