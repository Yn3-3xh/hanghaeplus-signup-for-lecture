package hanghaeplus.signupforlecture.application.lecture.domain.repository;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;

import java.util.Optional;

public interface LectureCapacityRepository {

    Optional<LectureCapacity> findByLectureIdGreaterThanZero(Long lectureId);

    void save(LectureCapacity lectureCapacity);
}
