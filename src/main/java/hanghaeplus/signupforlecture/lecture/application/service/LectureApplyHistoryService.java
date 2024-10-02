package hanghaeplus.signupforlecture.lecture.application.service;

import hanghaeplus.signupforlecture.lecture.domain.model.LectureApplyHistory;

import java.util.List;

public interface LectureApplyHistoryService {

    List<LectureApplyHistory> findSignedUpLectureHistories(Long userId);
}
