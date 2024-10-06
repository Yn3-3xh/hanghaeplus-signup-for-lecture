package hanghaeplus.signupforlecture.application.lecture.dto.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LectureAvailableRequestDto (
        LocalDate requestDate
) {
}
