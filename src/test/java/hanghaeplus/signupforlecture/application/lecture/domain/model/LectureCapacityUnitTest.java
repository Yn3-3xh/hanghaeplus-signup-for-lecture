package hanghaeplus.signupforlecture.application.lecture.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LectureCapacityUnitTest {

    @Test
    @DisplayName("신청 가능한 Slot - 1 동작 확인")
    void decreaseAvailableSlotTest() {
        // given
        Long lectureId = 1L;
        Lecture lecture = null;
        int maxSlot = 30;
        int availableSlot = 10;
        LectureCapacity lectureCapacity = LectureCapacity.builder()
                .lectureId(lectureId)
                .lecture(lecture)
                .maxSlot(maxSlot)
                .availableSlot(availableSlot)
                .build();

        // when
        LectureCapacity result = lectureCapacity.decreaseAvailableSlot();

        // then
        assertThat(result.availableSlot()).isEqualTo(9);
    }

    @Test
    @DisplayName("신청 가능한 Slot - 1 예외 발생")
    void fail_decreaseAvailableSlotTest() {
        // given
        Long lectureId = 1L;
        Lecture lecture = null;
        int maxSlot = 30;
        int availableSlot = 30;
        LectureCapacity lectureCapacity = LectureCapacity.builder()
                .lectureId(lectureId)
                .lecture(lecture)
                .maxSlot(maxSlot)
                .availableSlot(availableSlot)
                .build();

        // when
        for (int i = 1; i <= 30; i++) {
            lectureCapacity = lectureCapacity.decreaseAvailableSlot();
        }

        LectureCapacity finalLectureCapacity = lectureCapacity;
        IllegalStateException exception = assertThrows(IllegalStateException.class, finalLectureCapacity::decreaseAvailableSlot);

        // then
        assertThat(exception.getMessage()).isEqualTo("Slot을 신청할 수 없습니다.");
    }
}