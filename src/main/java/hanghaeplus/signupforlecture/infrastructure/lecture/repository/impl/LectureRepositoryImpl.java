package hanghaeplus.signupforlecture.infrastructure.lecture.repository.impl;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureRepository;
import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureEntity;
import hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureJpaRepository.findById(id)
                .map(LectureEntity::toDomain);
    }

    @Override
    public List<Lecture> findByIdIn(List<Long> ids) {
        return lectureJpaRepository.findByIdIn(ids).stream()
                .map(LectureEntity::toDomain)
                .toList();
    }

    @Override
    public List<Lecture> getAvailableLectures(LocalDate requestDate) {
        return lectureJpaRepository.getAvailableLectures(requestDate).stream()
                .map(LectureEntity::toDomain)
                .toList();
    }

    @Override
    public void save(Lecture lecture) {
        lectureJpaRepository.save(LectureEntity.fromDomain(lecture));
    }
}
