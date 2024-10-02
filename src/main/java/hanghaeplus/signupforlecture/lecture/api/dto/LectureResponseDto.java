package hanghaeplus.signupforlecture.lecture.api.dto;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecture;

import java.time.LocalDateTime;

public record LectureResponseDto(
    Long lectureId,
    String title,
    String lecturerName,
    int maxSlot,
    int availableSlot,
    LocalDateTime availableDate
) {
    public static LectureResponseDto fromDomain(Lecture lecture) {
        return new LectureResponseDto(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getLecturer().getName(),
                lecture.getMaxSlot(),
                lecture.getAvailableSlot(),
                lecture.getAvailableDate()
        );
    }
}
