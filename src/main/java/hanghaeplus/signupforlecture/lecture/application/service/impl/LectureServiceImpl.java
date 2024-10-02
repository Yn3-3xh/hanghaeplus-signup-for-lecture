package hanghaeplus.signupforlecture.lecture.application.service.impl;

import hanghaeplus.signupforlecture.lecture.application.service.LectureService;
import hanghaeplus.signupforlecture.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {
    // @Retryable 갓동민
    private final LectureRepository lectureRepository;

    @Override
    public List<Lecture> findAvailableLectures(LocalDateTime requestDate) {
        return null;
    }

    @Override
    public List<Lecture> findSignedUpLectures(List<Long> lectureIds) {
        return null;
    }

    @Override
    public void applyLecture(Long lectureId, Long aLong) {

    }
}
