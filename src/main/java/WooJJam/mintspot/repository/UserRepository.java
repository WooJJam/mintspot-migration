package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long register(User user) {
        em.persist(user);
        return user.getId();
    }
}
