package hanghaeplus.signupforlecture.infrastructure.lecture.repository.impl;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureApplyHistoryRepository;
import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureApplyHistoryEntity;
import hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa.LectureApplyHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureApplyHistoryRepositoryImpl implements LectureApplyHistoryRepository {

    private LectureApplyHistoryJpaRepository lectureApplyHistoryJpaRepository;

    @Override
    public List<LectureApplyHistory> getAllByUserId(Long id) {
        return lectureApplyHistoryJpaRepository.getAllByUserId(id).stream()
                .map(LectureApplyHistoryEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<LectureApplyHistory> findByLectureIdAndUserIdAndApplyStatus(Long lectureId, Long userId) {
        return lectureApplyHistoryJpaRepository.findByLectureIdAndUserIdAndApplyStatus(lectureId, userId);
    }

    @Override
    public LectureApplyHistory save(LectureApplyHistory lectureApplyHistory) {
        return lectureApplyHistoryJpaRepository.save(LectureApplyHistoryEntity.fromDomain(lectureApplyHistory))
                .toDomain();
    }
}