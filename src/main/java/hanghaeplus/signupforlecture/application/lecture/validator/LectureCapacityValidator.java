package hanghaeplus.signupforlecture.application.lecture.validator;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LectureCapacityValidator {

    public void validateLecture(LectureCapacity lectureCapacity) {
        if (Objects.isNull(lectureCapacity)) {
            throw new IllegalArgumentException("LectureCapacity는 null일 수 없습니다.");
        }
    }

    public void validateLectureId(Long lectureId) {
        if (lectureId == null) {
            throw new IllegalArgumentException("LectureId는 null일 수 없습니다.");
        }
    }
}
