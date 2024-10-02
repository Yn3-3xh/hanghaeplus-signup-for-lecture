package hanghaeplus.signupforlecture.application.lecture.domain.repository;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;

import java.util.List;

public interface LectureApplyHistoryRepository {
    List<LectureApplyHistory> findAllById(Long id);

    LectureApplyHistory save(LectureApplyHistory lectureApplyHistory);
}
