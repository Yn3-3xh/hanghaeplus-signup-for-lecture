package hanghaeplus.signupforlecture.application.user.validator;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public void validateUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId는 null일 수 없습니다.");
        }
    }
}
