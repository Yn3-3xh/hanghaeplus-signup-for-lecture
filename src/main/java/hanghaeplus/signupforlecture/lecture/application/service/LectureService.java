package hanghaeplus.signupforlecture.lecture.application.service;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecture;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureService {

    List<Lecture> findAvailableLectures(LocalDateTime requestDate);

    List<Lecture> findSignedUpLectures(List<Long> lectureIds);

    void applyLecture(Long lectureId, Long userId);
}
