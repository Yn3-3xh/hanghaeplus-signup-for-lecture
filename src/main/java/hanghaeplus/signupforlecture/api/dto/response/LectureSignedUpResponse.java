package hanghaeplus.signupforlecture.api.dto.response;

import hanghaeplus.signupforlecture.application.lecture.dto.response.LectureSignedUpResponseDto;

import java.time.LocalDate;

public record LectureSignedUpResponse(
        Long lectureId,
        String title,
        String lecturerName,
        LocalDate availableDate,
        int maxSlot,
        int availableSlot
) {
    public static LectureSignedUpResponse fromDto(LectureSignedUpResponseDto lectureSignedUpResponseDto) {
        return new LectureSignedUpResponse(
                lectureSignedUpResponseDto.lectureId(),
                lectureSignedUpResponseDto.title(),
                lectureSignedUpResponseDto.lecturerName(),
                lectureSignedUpResponseDto.availableDate(),
                lectureSignedUpResponseDto.maxSlot(),
                lectureSignedUpResponseDto.availableSlot()
        );
    }
}
