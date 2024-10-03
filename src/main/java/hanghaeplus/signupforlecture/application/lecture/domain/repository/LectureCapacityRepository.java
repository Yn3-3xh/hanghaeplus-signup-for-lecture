package hanghaeplus.signupforlecture.application.lecture.domain.repository;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface LectureCapacityRepository {

    Optional<LectureCapacity> findByLectureIdGreaterThanZeroLock(Long lectureId);

    void save(LectureCapacity lectureCapacity);
}
