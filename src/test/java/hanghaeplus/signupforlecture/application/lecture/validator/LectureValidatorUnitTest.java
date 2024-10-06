package hanghaeplus.signupforlecture.application.lecture.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class LectureValidatorUnitTest {

    private LectureValidator sut = new LectureValidator();

    @Test
    @DisplayName("요청 날짜가 null일 경우 예외 발생")
    void fail_validateDate1() {
        // given
        LocalDate requestDate = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateDate(requestDate))
                .withMessage("요청 날짜는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("요청 날짜가 현재 날짜 이전일 경우 예외 발생")
    void fail_validateDate2() {
        // given
        LocalDate requestDate = LocalDate.now().minusDays(1);

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateDate(requestDate))
                .withMessage("요청 날짜는 현재 날짜와 같거나 이후여야 합니다.");
    }

    @Test
    @DisplayName("요청 날짜가 현재 날짜인 경우 예외 발생 X")
    void validateDate() {
        // given
        LocalDate requestDate = LocalDate.now();

        // when & then
        assertThatNoException().isThrownBy(() -> {
            sut.validateDate(requestDate);
        });
    }

    @Test
    @DisplayName("강의 ID 목록이 null일 경우 예외 발생")
    void fail_validateIds1() {
        // given
        List<Long> ids = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateIds(ids))
                .withMessage("강의 ID 목록은 null일 수 없습니다.");
    }

    @Test
    @DisplayName("강의 ID 목록이 비어있을 경우 예외 발생")
    void fail_validateIds2() {
        // given
        List<Long> ids = new ArrayList<>();

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateIds(ids))
                .withMessage("강의 ID 목록은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("강의 ID 목록에 null이 포함된 경우 예외 발생")
    void fail_validateIds3() {
        // given
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(null);
        ids.add(3L);

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateIds(ids))
                .withMessage("강의 ID는 null이거나 0 이하일 수 없습니다.");
    }

    @Test
    @DisplayName("강의 ID 목록에 0 이하의 ID가 포함된 경우 예외 발생")
    void fail_validateIds4() {
        // given
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(0L);
        ids.add(3L);

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateIds(ids))
                .withMessage("강의 ID는 null이거나 0 이하일 수 없습니다.");
    }

    @Test
    @DisplayName("validateLectureId 예외발생 - lectureId가 null인 경우")
    void fail_validateLectureId() {
        // given
        Long lectureId = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateLectureId(lectureId))
                .withMessage("LectureId는 null일 수 없습니다.");
    }
}