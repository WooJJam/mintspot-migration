package WooJJam.mintspot.service;

import WooJJam.mintspot.domain.Profile;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;

    @Transactional
    public Long register(UserRegisterRequestBodyDto userRegisterRequestBodyDto, MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        User user = new User();
        Profile profile = new Profile();

        InputStream is = multipartFile.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucket, originalFilename, byteArrayInputStream, null)
                        .withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(putObjectRequest);
        String profileUrl = amazonS3.getUrl(bucket, originalFilename).toString();

        profile.createProfile(originalFilename, profileUrl);

        user.createUser(
                userRegisterRequestBodyDto.getEmail(),
                userRegisterRequestBodyDto.getPassword(),
                userRegisterRequestBodyDto.getGender(),
                userRegisterRequestBodyDto.getSexual(),
                profile
        );

        return userRepository.register(user);
    }

    public Optional<Long> login(UserLoginRequestBodyDto userLoginRequestBodyDto) {
        String email = userLoginRequestBodyDto.getEmail();
        String password = userLoginRequestBodyDto.getPassword();
        User findUser = userRepository.findByEmail(email);
        if (findUser != null && findUser.getEmail().equals(email) &&
        findUser.getPassword().equals(password)) {
            return Optional.of(findUser.getId());
        } else {
           return Optional.empty();
        }
    }

    public User findOne(Long userId) {
        return userRepository.findById(userId);
    }
}
