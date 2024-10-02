package hanghaeplus.signupforlecture.lecture.api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LectureAvailableRequestDto(
        @NotNull LocalDateTime requestDate
) {
}
