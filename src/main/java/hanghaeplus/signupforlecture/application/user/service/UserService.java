package hanghaeplus.signupforlecture.application.user.service;

import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.application.user.domain.repository.UserRepository;
import hanghaeplus.signupforlecture.application.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public User getUser(Long userId) {
        userValidator.validateUserId(userId);

        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("등록된 사용자가 아닙니다."));
    }
}
