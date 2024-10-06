package hanghaeplus.signupforlecture.application.lecture.domain.repository;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;

import java.util.List;
import java.util.Optional;

public interface LectureCapacityRepository {

    Optional<LectureCapacity> findById(Long lectureId);

    List<LectureCapacity> findByLectureIdInOrderByLectureId(List<Long> lectureIds);

    Optional<LectureCapacity> findByLectureIdGreaterThanZeroLock(Long lectureId);

    void save(LectureCapacity lectureCapacity);
}
