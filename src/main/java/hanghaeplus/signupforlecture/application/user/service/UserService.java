package hanghaeplus.signupforlecture.application.user.service;

import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.application.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("등록된 사용자가 아닙니다."));
    }
}
