package hanghaeplus.signupforlecture.api.dto.request;

import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureApplyRequestDto;
import jakarta.validation.constraints.NotNull;

public record LectureApplyRequest(
    @NotNull Long userId
) {
    public LectureApplyRequestDto toDto() {
        return LectureApplyRequestDto.builder()
                .userId(this.userId)
                .build();
    }
}
