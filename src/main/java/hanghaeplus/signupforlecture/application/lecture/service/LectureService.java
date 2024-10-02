package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lecture> findAvailableLectures(LocalDate requestDate) {
        List<Lecture> lectures = lectureRepository.findAvailableLectures(requestDate);
        if (lectures.isEmpty()) {
            throw new NoSuchElementException("요청한 날짜에 신청 가능한 강의가 없습니다.");
        }

        return lectures;
    }

    public List<Lecture> findSignedUpLectures(List<Long> lectureIds) {
        return null;
    }

    public void applyLecture(Long lectureId, Long aLong) {

    }
}
