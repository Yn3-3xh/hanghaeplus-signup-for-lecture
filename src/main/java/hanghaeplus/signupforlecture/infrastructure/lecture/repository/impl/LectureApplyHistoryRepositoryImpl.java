package hanghaeplus.signupforlecture.infrastructure.lecture.repository.impl;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureApplyHistoryRepository;
import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureApplyHistoryEntity;
import hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa.LectureApplyHistoryJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LectureApplyHistoryRepositoryImpl implements LectureApplyHistoryRepository {

    private LectureApplyHistoryJpaRepository lectureApplyHistoryJpaRepository;

    @Override
    public List<LectureApplyHistory> findAllById(Long id) {
        return lectureApplyHistoryJpaRepository.findAllByUserId(id).stream()
                .map(LectureApplyHistoryEntity::toDomain)
                .toList();
    }

    @Override
    public LectureApplyHistory save(LectureApplyHistory lectureApplyHistory) {
        return lectureApplyHistoryJpaRepository.save(LectureApplyHistoryEntity.fromDomain(lectureApplyHistory))
                .toDomain();
    }
}