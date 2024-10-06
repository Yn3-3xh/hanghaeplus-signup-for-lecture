package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import hanghaeplus.signupforlecture.application.lecture.validator.LectureCapacityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

//        Optional<LectureCapacity> test = lectureCapacityRepository.findById(lectureCapacity.lectureId());
//        System.out.println("가가가 " + test.get().availableSlot());

        LectureCapacity capacity = lectureCapacity.decreaseAvailableSlot();
        lectureCapacityRepository.save(capacity);
    }

    public List<LectureCapacity> getAvailableSlot(List<Lecture> availableLectureIds) {

        List<Long> lectureIds = availableLectureIds.stream()
                .map(Lecture::id)
                .toList();
        return lectureCapacityRepository.findByLectureIdInOrderByLectureId(lectureIds);
    }
}
