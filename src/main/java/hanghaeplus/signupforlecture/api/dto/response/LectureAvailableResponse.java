package hanghaeplus.signupforlecture.api.dto.response;

import hanghaeplus.signupforlecture.application.lecture.dto.response.LectureAvailableResponseDto;

import java.time.LocalDate;

public record LectureAvailableResponse(
    Long lectureId,
    String title,
    String lecturerName,
    LocalDate availableDate
//    int maxSlot,
//    int availableSlot
) {
    public static LectureAvailableResponse fromDto(LectureAvailableResponseDto lectureAvailableResponseDto) {
        return new LectureAvailableResponse(
                lectureAvailableResponseDto.lectureId(),
                lectureAvailableResponseDto.title(),
                lectureAvailableResponseDto.lecturerName(),
                lectureAvailableResponseDto.availableDate()
//                lectureAvailableResponseDto.maxSlot(),
//                lectureAvailableResponseDto.availableSlot()
        );
    }
}
