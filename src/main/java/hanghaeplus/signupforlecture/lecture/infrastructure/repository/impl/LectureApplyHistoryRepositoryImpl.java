package hanghaeplus.signupforlecture.lecture.infrastructure.repository.impl;

import hanghaeplus.signupforlecture.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.lecture.domain.repository.LectureApplyHistoryRepository;
import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LectureApplyHistoryEntity;
import hanghaeplus.signupforlecture.lecture.infrastructure.repository.LectureApplyHistoryDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LectureApplyHistoryRepositoryImpl implements LectureApplyHistoryRepository {

    private LectureApplyHistoryDataRepository lectureApplyHistoryDataRepository;

    @Override
    public List<LectureApplyHistory> findAllById(Long id) {
        return lectureApplyHistoryDataRepository.findAllByUserId(id).stream()
                .map(LectureApplyHistoryEntity::toDomain)
                .toList();
    }

    @Override
    public LectureApplyHistory save(LectureApplyHistory lectureApplyHistory) {
        return lectureApplyHistoryDataRepository.save(LectureApplyHistoryEntity.fromDomain(lectureApplyHistory))
                .toDomain();
    }
}