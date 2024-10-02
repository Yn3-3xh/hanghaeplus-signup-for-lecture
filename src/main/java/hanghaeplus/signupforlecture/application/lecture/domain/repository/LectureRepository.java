package hanghaeplus.signupforlecture.application.lecture.domain.repository;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {

    Optional<Lecture> findById(Long id);

    List<Lecture> findByIdIn(List<Long> ids);

    List<Lecture> findAvailableLectures(LocalDate requestDate);

    Lecture save(Lecture lecture);
}
