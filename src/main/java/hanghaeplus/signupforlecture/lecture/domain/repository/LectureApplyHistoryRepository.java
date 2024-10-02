package hanghaeplus.signupforlecture.lecture.domain.repository;

import hanghaeplus.signupforlecture.lecture.domain.model.LectureApplyHistory;

import java.util.List;

public interface LectureApplyHistoryRepository {
    List<LectureApplyHistory> findAllById(Long id);

    LectureApplyHistory save(LectureApplyHistory lectureApplyHistory);
}
