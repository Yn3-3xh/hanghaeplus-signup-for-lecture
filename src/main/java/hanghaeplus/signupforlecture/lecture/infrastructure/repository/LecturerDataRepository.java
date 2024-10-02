package hanghaeplus.signupforlecture.lecture.infrastructure.repository;

import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LecturerEntity;

import java.util.Optional;

public interface LecturerDataRepository {
    Optional<LecturerEntity> findById(Long id);
    LecturerEntity save(LecturerEntity lecturerEntity);
}
