package hanghaeplus.signupforlecture.application.lecture.dto.request;

import lombok.Builder;

@Builder
public record LectureApplyRequestDto (
        Long userId
) {
}
