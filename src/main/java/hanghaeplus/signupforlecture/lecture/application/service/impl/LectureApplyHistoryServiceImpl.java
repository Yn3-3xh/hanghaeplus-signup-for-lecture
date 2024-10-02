package hanghaeplus.signupforlecture.lecture.application.service.impl;

import hanghaeplus.signupforlecture.lecture.application.service.LectureApplyHistoryService;
import hanghaeplus.signupforlecture.lecture.domain.model.LectureApplyHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureApplyHistoryServiceImpl implements LectureApplyHistoryService {
    @Override
    public List<LectureApplyHistory> findSignedUpLectureHistories(Long userId) {
        return null;
    }
}
