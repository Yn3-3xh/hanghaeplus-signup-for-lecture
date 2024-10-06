package hanghaeplus.signupforlecture.application.user.validator;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserValidatorUnitTest {

    private UserValidator sut = new UserValidator();

    @Test
    @DisplayName("validateUserId 예외발생")
    void fail_validateUserId() {
        // given
        Long userId = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateUserId(userId))
                .withMessage("UserId는 null일 수 없습니다.");
    }
}