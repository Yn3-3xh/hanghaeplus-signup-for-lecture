package hanghaeplus.signupforlecture.application.lecture.dto.response;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;

import java.time.LocalDate;

public record LectureAvailableResponseDto(
    Long lectureId,
    String title,
    String lecturerName,
    LocalDate availableDate,
    int maxSlot,
    int availableSlot
) {
    public static LectureAvailableResponseDto fromDomain(Lecture lecture) {
        return new LectureAvailableResponseDto(
                lecture.id(),
                lecture.title(),
                lecture.lecturer().name(),
                lecture.availableDate(),
                lecture.lectureCapacity().maxSlot(),
                lecture.lectureCapacity().availableSlot()
        );
    }
}
