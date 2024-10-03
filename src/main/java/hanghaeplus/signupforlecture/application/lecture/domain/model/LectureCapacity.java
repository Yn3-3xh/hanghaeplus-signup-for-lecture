package hanghaeplus.signupforlecture.application.lecture.domain.model;

import lombok.Builder;

import java.util.NoSuchElementException;

@Builder
public record LectureCapacity (

    Long id,
    Long lectureId,
    int maxSlot,
    int availableSlot
) {
    public LectureCapacity decreaseAvailableSlot() {
        int afterAvailableSlot = this.availableSlot - 1;
        if (afterAvailableSlot < 0) {
            throw new IllegalStateException("Slot을 신청할 수 없습니다.");
        }

        return new LectureCapacity(id, lectureId, maxSlot, afterAvailableSlot);
    }
}
