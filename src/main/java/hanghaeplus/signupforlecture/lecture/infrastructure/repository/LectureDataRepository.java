package hanghaeplus.signupforlecture.lecture.infrastructure.repository;

import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LectureEntity;

import java.util.List;
import java.util.Optional;

public interface LectureDataRepository {

    Optional<LectureEntity> findById(Long id);
    List<LectureEntity> findByIdIn(List<Long> ids);
    List<LectureEntity> findAvailableLectures();
    LectureEntity save(LectureEntity lectureEntity);
}
