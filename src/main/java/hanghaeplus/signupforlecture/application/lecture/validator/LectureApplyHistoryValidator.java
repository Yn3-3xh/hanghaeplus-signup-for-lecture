package hanghaeplus.signupforlecture.application.lecture.validator;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LectureApplyHistoryValidator {

    public void validateInsertHistoryParam(Lecture lecture, Long userId) {
        validateLecture(lecture);
        validateId(userId);
    }

    public void validateId(Long... ids) {
        if (ids == null) {
            throw new IllegalArgumentException("Id는 null일 수 없습니다.");
        }
        for (Long id : ids) {
            if (id == null) {
                throw new IllegalArgumentException("Id는 null일 수 없습니다.");
            }
        }
    }

    public void validateLecture(Lecture lecture) {
        if (Objects.isNull(lecture)) {
            throw new IllegalArgumentException("Lecture는 null일 수 없습니다.");
        }
    }
}
