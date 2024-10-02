package hanghaeplus.signupforlecture.lecture.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LectureApplyHistory {

    private Long id;

    private Lecture lecture;

    private Long userId;

    private ApplyStatus applyStatus;

    @Builder
    public LectureApplyHistory(Long id,
                               Lecture lecture,
                               Long userId,
                               ApplyStatus applyStatus) {
        this.id = id;
        this.lecture = lecture;
        this.userId = userId;
        this.applyStatus = applyStatus;
    }
}
