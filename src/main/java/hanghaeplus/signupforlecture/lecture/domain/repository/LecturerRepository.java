package hanghaeplus.signupforlecture.lecture.domain.repository;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecturer;

import java.util.Optional;

public interface LecturerRepository {
    Optional<Lecturer> findById(Long id);
    Lecturer save(Lecturer lecturer);
}
