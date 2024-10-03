package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LectureCapacityService {

    private final LectureCapacityRepository lectureCapacityRepository;

    public void applyAvailableSlot(Long lectureId) {
        LectureCapacity lectureCapacity = lectureCapacityRepository.findByLectureIdGreaterThanZero(lectureId)
                .orElseThrow(() -> new NoSuchElementException("신청 가능한 Slot이 없습니다."));

        LectureCapacity capacity = lectureCapacity.decreaseAvailableSlot();
        lectureCapacityRepository.save(capacity);
    }
}
