package hanghaeplus.signupforlecture.application.user.service;

import hanghaeplus.signupforlecture.application.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("유저가 없으면 예외발생")
    void fail_getUser() {
        // given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            sut.getUser(userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("등록된 사용자가 아닙니다.");
    }
}