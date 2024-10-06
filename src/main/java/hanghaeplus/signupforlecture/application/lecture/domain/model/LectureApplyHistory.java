package hanghaeplus.signupforlecture.application.lecture.domain.model;

import hanghaeplus.signupforlecture.application.lecture.domain.model.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record LectureApplyHistory (

    Long id,
    Lecture lecture,
    Long userId,
    ApplyStatus applyStatus
) {
}
