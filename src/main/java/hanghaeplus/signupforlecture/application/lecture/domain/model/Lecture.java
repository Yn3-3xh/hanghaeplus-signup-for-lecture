package hanghaeplus.signupforlecture.application.lecture.domain.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Lecture (

    Long id,
    String title,
    Lecturer lecturer,
//    LectureCapacity lectureCapacity,
    Long lectureCapacityId,
    LocalDate availableDate
) {
}
