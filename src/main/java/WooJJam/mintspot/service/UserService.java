package WooJJam.mintspot.service;

import WooJJam.mintspot.domain.User;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
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
    public Long register(UserRegisterRequestBodyDto userRegisterRequestBodyDto) {
        User user = new User();
        user.createUser(
                userRegisterRequestBodyDto.getEmail(),
                userRegisterRequestBodyDto.getPassword(),
                userRegisterRequestBodyDto.getGender(),
                userRegisterRequestBodyDto.getSexual()
        );
        return userRepository.register(user);
    }
}
