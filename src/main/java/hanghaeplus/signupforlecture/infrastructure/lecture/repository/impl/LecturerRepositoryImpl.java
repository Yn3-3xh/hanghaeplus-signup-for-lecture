package hanghaeplus.signupforlecture.infrastructure.lecture.repository.impl;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecturer;
import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LecturerEntity;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LecturerRepository;
import hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa.LecturerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LecturerRepositoryImpl implements LecturerRepository {

    private final LecturerJpaRepository lecturerJpaRepository;

    @Override
    public Optional<Lecturer> findById(Long id) {
        return lecturerJpaRepository.findById(id)
                .map(LecturerEntity::toDomain);
    }

    @Override
    public Lecturer save(Lecturer lecturer) {
        return lecturerJpaRepository.save(LecturerEntity.fromDomain(lecturer))
                .toDomain();
    }
}
