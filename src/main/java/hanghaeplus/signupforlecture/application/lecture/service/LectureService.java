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

    public List<Lecture> getAvailableLectures(LocalDate requestDate) {

        List<Lecture> lectures = lectureRepository.getAvailableLectures(requestDate);
        if (lectures.isEmpty()) {
            throw new NoSuchElementException("요청한 날짜에 신청 가능한 강의가 없습니다.");
        }

        return lectures;
    }

    public List<Lecture> findSignedUpLectures(List<Long> lectureIds) {

        return lectureRepository.findByIdIn(lectureIds);
    }

    public Lecture getLecture(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NoSuchElementException("등록된 강의가 아닙니다."));
    }
}
