package hanghaeplus.signupforlecture.application.lecture.validator;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class LectureCapacityValidatorUnitTest {

    private LectureCapacityValidator sut = new LectureCapacityValidator();

    @Test
    @DisplayName("validateLecture 예외발생")
    void fail_validateLectureTest() {
        // given
        LectureCapacity nullLectureCapacity = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.validateLecture(nullLectureCapacity))
                .withMessage("LectureCapacity는 null일 수 없습니다.");
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