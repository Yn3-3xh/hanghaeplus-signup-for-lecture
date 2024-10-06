package hanghaeplus.signupforlecture.application.lecture.validator;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class LectureApplyHistoryValidatorUnitTest {

    private LectureApplyHistoryValidator sut = new LectureApplyHistoryValidator();

    @Test
    @DisplayName("validateLecture 예외발생")
    void fail_validateLectureTest() {
        // given
        Lecture nullLecture = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateLecture(nullLecture))
                .withMessage("Lecture는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("validateId 예외발생 - null인 경우")
    void fail_validateId1() {
        // given
        Long[] ids = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateId(ids))
                .withMessage("Id는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("validateId 예외발생 - ID가 null인 경우")
    void fail_validateId2() {
        // given
        Long[] ids = {1L, null, 2L};

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateId(ids))
                .withMessage("Id는 null일 수 없습니다.");
    }
}