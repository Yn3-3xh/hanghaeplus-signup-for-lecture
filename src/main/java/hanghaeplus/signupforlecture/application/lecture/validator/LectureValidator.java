package hanghaeplus.signupforlecture.application.lecture.validator;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LectureValidator {

    public void validateDate(LocalDate requestDate) {
        if (requestDate == null) {
            throw new IllegalArgumentException("요청 날짜는 null일 수 없습니다.");
        }
        if (requestDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("요청 날짜는 현재 날짜와 같거나 이후여야 합니다.");
        }
    }

    public void validateIds(List<Long> ids) {
        if (ids == null) {
            throw new IllegalArgumentException("강의 ID 목록은 null일 수 없습니다.");
        }
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("강의 ID 목록은 비어있을 수 없습니다.");
        }
        if (ids.stream().anyMatch(id -> id == null || id <= 0)) {
            throw new IllegalArgumentException("강의 ID는 null이거나 0 이하일 수 없습니다.");
        }
    }

    public void validateLectureId(Long lectureId) {
        if (lectureId == null) {
            throw new IllegalArgumentException("LectureId는 null일 수 없습니다.");
        }
    }
}
