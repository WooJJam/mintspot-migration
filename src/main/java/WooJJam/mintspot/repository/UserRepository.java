package WooJJam.mintspot.repository;

import WooJJam.mintspot.domain.user.User;
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

    public void login(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
    }

    public User findByEmail(String email) {
        return em.createQuery("select u from User u where u.email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u" +
                        " join fetch u.chats", User.class)
                .getResultList();
    }

    public Long register(User user) {
        em.persist(user);
        return user.getId();
    }


}
