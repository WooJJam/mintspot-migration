package WooJJam.mintspot.service;

import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Optional<Long> login(UserLoginRequestBodyDto userLoginRequestBodyDto) {
        String email = userLoginRequestBodyDto.getEmail();
        String password = userLoginRequestBodyDto.getPassword();
        User findUser = userRepository.findByEmail(email);
        if (findUser.getEmail().equals(email) &&
        findUser.getPassword().equals(password)) {
            return Optional.of(findUser.getId());
        } else {
           return Optional.empty();
        }
    }
}
