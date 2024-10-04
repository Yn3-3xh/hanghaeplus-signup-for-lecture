package hanghaeplus.signupforlecture.application.lecture.facade;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureApplyRequestDto;
import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureAvailableRequestDto;
import hanghaeplus.signupforlecture.application.lecture.dto.response.LectureAvailableResponseDto;
import hanghaeplus.signupforlecture.application.lecture.dto.response.LectureSignedUpResponseDto;
import hanghaeplus.signupforlecture.application.lecture.service.LectureApplyHistoryService;
import hanghaeplus.signupforlecture.application.lecture.service.LectureCapacityService;
import hanghaeplus.signupforlecture.application.lecture.service.LectureService;
import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.application.user.service.UserService;
import hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa.LectureCapacityJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor
@Slf4j
public class LectureFacade {

    private final UserService userService;

    private final LectureService lectureService;
    private final LectureApplyHistoryService lectureApplyHistoryService;
    private final LectureCapacityService lectureCapacityService;

    private final ReentrantLock lock;

    public List<LectureAvailableResponseDto> getAvailableLectures(LectureAvailableRequestDto lectureAvailableRequestDto) {
        List<Lecture> availableLectures = lectureService.getAvailableLectures(lectureAvailableRequestDto.requestDate());

        return availableLectures.stream()
                .map(LectureAvailableResponseDto::fromDomain)
                .toList();
    }

    public List<LectureSignedUpResponseDto> getSignedUpLectures(Long userId) {
        User user = userService.getUser(userId);

        List<Long> signedUpLectureIds = lectureApplyHistoryService.findSignedUpLectureHistories(user.id());
        List<Lecture> signedUpLectures = lectureService.findSignedUpLectures(signedUpLectureIds);

        return signedUpLectures.stream()
                .map(LectureSignedUpResponseDto::fromDomain)
                .toList();
    }

    @Transactional
    public void applyLecture(Long lectureId, LectureApplyRequestDto lectureApplyRequestDto) {
        User user = userService.getUser(lectureApplyRequestDto.userId());
        Lecture lecture = lectureService.getLecture(lectureId);

//        lock.lock();
        try {
            // 중복 체크 (history 테이블)
            lectureApplyHistoryService.checkApplyLectureHistory(lecture.id(), user.id());

            // 신청 가능한 Slot (lectureCapacity 테이블) 조회 <락 획득>
            LectureCapacity lectureCapacity = lectureCapacityService.getAvailableSlotLock(lecture.id());

            System.out.println("Attempting to apply lecture, available slots: " + lectureCapacity.availableSlot());
            // 30 30 30 30 > 29 29 29 29 29 >>>>>> ㅠㅠ gg

            // 내역 저장 (history 테이블)
            lectureApplyHistoryService.insertAppliedHistory(lecture, user.id());

            // 신청 가능 Slot - 1 로직 >> 저장 (lectureCapacity 테이블)
            lectureCapacityService.applyAvailableSlot(lectureCapacity);
            // 2명으로 했을때 update가 처음 한번만 나간다 > 왜 ㅡㅡ
            // 40명으로 하면 40명 내역 저장 >> slot도 30 N개, 29 N개, ... 저장

        } catch (Exception e) {
            lectureApplyHistoryService.insertFailedHistory(lecture, user.id());
        } finally {
//            lock.unlock();
        }
    }
}
