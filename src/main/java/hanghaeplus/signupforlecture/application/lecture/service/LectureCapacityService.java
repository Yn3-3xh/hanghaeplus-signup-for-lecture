package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import hanghaeplus.signupforlecture.application.lecture.validator.LectureCapacityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LectureCapacityService {

    private final LectureCapacityRepository lectureCapacityRepository;
    private final LectureCapacityValidator lectureCapacityValidator;

    public LectureCapacity getAvailableSlotLock(Long lectureId) {
        lectureCapacityValidator.validateLectureId(lectureId);

        return lectureCapacityRepository.findByLectureIdGreaterThanZeroLock(lectureId)
                .orElseThrow(() -> new NoSuchElementException("신청 가능한 Slot이 없습니다."));
    }

    public void applyAvailableSlot(LectureCapacity lectureCapacity) {
        lectureCapacityValidator.validateLecture(lectureCapacity);

        LectureCapacity capacity = lectureCapacity.decreaseAvailableSlot();
        System.out.println("가나다");
        System.out.println(capacity.availableSlot());

        lectureCapacityRepository.save(capacity);
    }
}
