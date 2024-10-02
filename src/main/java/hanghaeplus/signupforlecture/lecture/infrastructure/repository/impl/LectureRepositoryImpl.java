package hanghaeplus.signupforlecture.lecture.infrastructure.repository.impl;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.lecture.domain.repository.LectureRepository;
import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LectureEntity;
import hanghaeplus.signupforlecture.lecture.infrastructure.repository.LectureDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureDataRepository lectureDataRepository;

    public LectureDataRepository getLectureDataRepository() {
        return lectureDataRepository;
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureDataRepository.findById(id)
                .map(LectureEntity::toDomain);
    }

    @Override
    public List<Lecture> findByIdIn(List<Long> ids) {
        return lectureDataRepository.findByIdIn(ids).stream()
                .map(LectureEntity::toDomain)
                .toList();
    }

    @Override
    public List<Lecture> findAvailableLectures() {
        return lectureDataRepository.findAvailableLectures().stream()
                .map(LectureEntity::toDomain)
                .toList();
    }

    @Override
    public Lecture save(Lecture lecture) {
        return lectureDataRepository.save(LectureEntity.fromDomain(lecture))
                .toDomain();
    }
}
