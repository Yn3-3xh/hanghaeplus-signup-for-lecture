package hanghaeplus.signupforlecture.application.lecture.domain.repository;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;

import java.util.List;
import java.util.Optional;

public interface LectureApplyHistoryRepository {
    List<LectureApplyHistory> getAllByUserId(Long id);

    Optional<LectureApplyHistory> findByLectureIdAndUserIdAndAppliedStatus(Long lectureId, Long userId);

    LectureApplyHistory save(LectureApplyHistory lectureApplyHistory);

    List<LectureApplyHistory> findByLectureIdAndAppliedStatus(Long lectureId);

    List<LectureApplyHistory> findByLectureIdAndFailedStatus(Long lectureId);
}
