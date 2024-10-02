package hanghaeplus.signupforlecture.lecture.infrastructure.repository;

import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LectureApplyHistoryEntity;

import java.util.List;

public interface LectureApplyHistoryDataRepository {

    List<LectureApplyHistoryEntity> findAllByUserId(Long id);

    LectureApplyHistoryEntity save(LectureApplyHistoryEntity lectureApplyHistoryEntity);
}
