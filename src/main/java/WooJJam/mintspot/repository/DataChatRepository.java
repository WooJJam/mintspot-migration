package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.chat.Chat;
import org.springframework.data.repository.CrudRepository;

public interface DataChatRepository extends CrudRepository<Chat, Long> {
}
