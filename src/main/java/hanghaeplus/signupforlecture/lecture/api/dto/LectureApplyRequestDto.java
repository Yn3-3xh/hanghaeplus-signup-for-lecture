package hanghaeplus.signupforlecture.lecture.api.dto;

import jakarta.validation.constraints.NotNull;

public record LectureApplyRequestDto(
    @NotNull Long userId
) {

}
