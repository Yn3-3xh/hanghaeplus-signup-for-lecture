package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureRepository;
import hanghaeplus.signupforlecture.application.lecture.validator.LectureValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureValidator lectureValidator;

    @Transactional(readOnly = true)
    public List<Lecture> getAvailableLectures(LocalDate requestDate) {
        lectureValidator.validateDate(requestDate);

        List<Lecture> availableLectures = lectureRepository.findByAvailableDateOrderById(requestDate);
        if (availableLectures.isEmpty()) {
            throw new NoSuchElementException("요청한 날짜에 신청 가능한 강의가 없습니다.");
        }

        return availableLectures;
    }

    @Transactional(readOnly = true)
    public List<Lecture> findSignedUpLectures(List<Long> lectureIds) {
        lectureValidator.validateIds(lectureIds);

        return lectureRepository.findByIdIn(lectureIds);
    }

    public Lecture getLecture(Long lectureId) {
        lectureValidator.validateLectureId(lectureId);

        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NoSuchElementException("등록된 강의가 아닙니다."));
    }
}
