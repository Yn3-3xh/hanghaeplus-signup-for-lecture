package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.application.lecture.domain.model.enums.ApplyStatus;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureApplyHistoryRepository;
import hanghaeplus.signupforlecture.application.lecture.validator.LectureApplyHistoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureApplyHistoryService {

    private final LectureApplyHistoryRepository lectureApplyHistoryRepository;
    private final LectureApplyHistoryValidator lectureApplyHistoryValidator;

    public List<Long> findSignedUpLectureHistories(Long userId) {
        lectureApplyHistoryValidator.validateId(userId);

        List<LectureApplyHistory> lectureApplyHistories = lectureApplyHistoryRepository.getAllByUserId(userId);
        return lectureApplyHistories.stream()
                .map(lectureApplyHistory -> lectureApplyHistory.lecture().id())
                .toList();
    }

    public void checkApplyLectureHistory(Long lectureId, Long userId) {
        lectureApplyHistoryValidator.validateId(lectureId, userId);

        Optional<LectureApplyHistory> lectureApplyHistory = lectureApplyHistoryRepository.findByLectureIdAndUserIdAndApplyStatus(userId, lectureId);
        if (lectureApplyHistory.isPresent()) {
            throw new IllegalStateException("해당 강의에 이미 신청한 이력이 있습니다.");
        }
    }

    public void insertAppliedHistory(Lecture lecture, Long userId) {
        lectureApplyHistoryValidator.validateInsertHistoryParam(lecture, userId);

        LectureApplyHistory history = LectureApplyHistory.builder()
                .lecture(lecture)
                .userId(userId)
                .applyStatus(ApplyStatus.APPLIED)
                .build();
        lectureApplyHistoryRepository.save(history);
    }

    public void insertFailedHistory(Lecture lecture, Long userId) {
        lectureApplyHistoryValidator.validateInsertHistoryParam(lecture, userId);

        LectureApplyHistory history = LectureApplyHistory.builder()
                .lecture(lecture)
                .userId(userId)
                .applyStatus(ApplyStatus.FAILED)
                .build();
        lectureApplyHistoryRepository.save(history);
    }
}
