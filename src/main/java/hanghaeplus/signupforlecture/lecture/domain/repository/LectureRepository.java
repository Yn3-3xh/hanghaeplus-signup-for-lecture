package hanghaeplus.signupforlecture.lecture.domain.repository;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findById(Long id);
    List<Lecture> findByIdIn(List<Long> ids);
    List<Lecture> findAvailableLectures();
    Lecture save(Lecture lecture);
}
