package hanghaeplus.signupforlecture.lecture.infrastructure.repository.impl;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecturer;
import hanghaeplus.signupforlecture.lecture.domain.repository.LecturerRepository;
import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LecturerEntity;
import hanghaeplus.signupforlecture.lecture.infrastructure.repository.LecturerDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LecturerRepositoryImpl implements LecturerRepository {

    private final LecturerDataRepository lecturerDataRepository;

    @Override
    public Optional<Lecturer> findById(Long id) {
        return lecturerDataRepository.findById(id)
                .map(LecturerEntity::toDomain);
    }

    @Override
    public Lecturer save(Lecturer lecturer) {
        return lecturerDataRepository.save(LecturerEntity.fromDomain(lecturer))
                .toDomain();
    }
}
