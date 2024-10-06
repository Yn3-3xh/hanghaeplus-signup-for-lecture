package hanghaeplus.signupforlecture.api.dto.request;

import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureAvailableRequestDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LectureAvailableRequest(
        @NotNull LocalDate requestDate
) {
    public LectureAvailableRequestDto toDto() {
        return LectureAvailableRequestDto.builder()
                .requestDate(this.requestDate)
                .build();
    }
}
