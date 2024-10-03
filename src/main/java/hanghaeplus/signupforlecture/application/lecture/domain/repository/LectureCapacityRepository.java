package hanghaeplus.signupforlecture.application.lecture.domain.repository;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import jakarta.persistence.LockModeType;

import java.util.Optional;

public interface LectureCapacityRepository {

    Optional<LectureCapacity> findById(Long lectureId);

    Optional<LectureCapacity> findByLectureIdGreaterThanZeroLock(Long lectureId);

    void save(LectureCapacity lectureCapacity);
}
