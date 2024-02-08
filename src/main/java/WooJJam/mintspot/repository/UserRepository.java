package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.User;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public User findById(Long userId) {
        return em.find(User.class, userId);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Long register(User user) {
        em.persist(user);
        return user.getId();
    }

}
