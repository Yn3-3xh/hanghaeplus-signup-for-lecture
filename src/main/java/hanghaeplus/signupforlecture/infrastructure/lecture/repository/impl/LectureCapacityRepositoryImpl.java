package hanghaeplus.signupforlecture.infrastructure.lecture.repository.impl;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureCapacityEntity;
import hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa.LectureCapacityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureCapacityRepositoryImpl implements LectureCapacityRepository {

    private final LectureCapacityJpaRepository lectureCapacityJpaRepository;

    @Override
    public Optional<LectureCapacity> findById(Long lectureId) {
        return lectureCapacityJpaRepository.findById(lectureId)
                .map(LectureCapacityEntity::toDomain);
    }

    @Override
    public List<LectureCapacity> findByLectureIdInOrderByLectureId(List<Long> lectureIds) {
        return lectureCapacityJpaRepository.findByLectureIdInOrderByLectureId(lectureIds).stream()
                .map(LectureCapacityEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<LectureCapacity> findByLectureIdGreaterThanZeroLock(Long lectureId) {
        return lectureCapacityJpaRepository.findByLectureIdGreaterThanZeroLock(lectureId)
                .map(LectureCapacityEntity::toDomain);
    }

    @Override
    public void save(LectureCapacity lectureCapacity) {
        lectureCapacityJpaRepository.save(LectureCapacityEntity.fromDomain(lectureCapacity));
    }
}
