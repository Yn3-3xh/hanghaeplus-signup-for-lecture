package hanghaeplus.signupforlecture.application.lecture.domain.model;

import lombok.Builder;

@Builder
public record LectureCapacity (

    Long lectureId,
    Lecture lecture,
    int maxSlot,
    int availableSlot
) {
}
