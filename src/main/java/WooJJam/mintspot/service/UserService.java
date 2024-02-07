package WooJJam.mintspot.service;

import WooJJam.mintspot.domain.User;
import WooJJam.mintspot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long register(User reqUesr) {
        User user = new User();
        user.createUser(reqUesr.getEmail(), reqUesr.getPassword(), reqUesr.getGender(), reqUesr.getSexual());
        return userRepository.register(user);
    }
}
