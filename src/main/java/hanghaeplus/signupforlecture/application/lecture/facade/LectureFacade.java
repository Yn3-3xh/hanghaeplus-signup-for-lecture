package hanghaeplus.signupforlecture.application.lecture.facade;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureApplyRequestDto;
import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureAvailableRequestDto;
import hanghaeplus.signupforlecture.application.lecture.dto.response.LectureAvailableResponseDto;
import hanghaeplus.signupforlecture.application.lecture.dto.response.LectureSignedUpResponseDto;
import hanghaeplus.signupforlecture.application.lecture.service.LectureApplyHistoryService;
import hanghaeplus.signupforlecture.application.lecture.service.LectureCapacityService;
import hanghaeplus.signupforlecture.application.lecture.service.LectureService;
import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {

    private final UserService userService;

    private final LectureService lectureService;
    private final LectureApplyHistoryService lectureApplyHistoryService;
    private final LectureCapacityService lectureCapacityService;

    public List<LectureAvailableResponseDto> getAvailableLectures(LectureAvailableRequestDto lectureAvailableRequestDto) {
        List<Lecture> availableLectureIds = lectureService.getAvailableLectures(lectureAvailableRequestDto.requestDate());
        List<LectureCapacity> availableLectures = lectureCapacityService.getAvailableSlot(availableLectureIds);

        return LectureAvailableResponseDto.fromDomains(availableLectureIds, availableLectures);
    }

    @Transactional
    public void applyLecture(Long lectureId, LectureApplyRequestDto lectureApplyRequestDto) {
        User user = userService.getUser(lectureApplyRequestDto.userId());
        Lecture lecture = lectureService.getLecture(lectureId);

        try {
            // 중복 체크 (history 테이블)
            lectureApplyHistoryService.checkApplyLectureHistory(lecture.id(), user.id());

            // 신청 가능한 Slot (lectureCapacity 테이블) 조회 <락 획득>
            LectureCapacity lectureCapacity = lectureCapacityService.getAvailableSlotLock(lecture.id());

            // 내역 저장 (history 테이블)
            lectureApplyHistoryService.insertAppliedHistory(lecture, user.id());

            // 신청 가능 Slot - 1 로직 >> 저장 (lectureCapacity 테이블)
            lectureCapacityService.applyAvailableSlot(lectureCapacity);
        } catch (RuntimeException e) {
            lectureApplyHistoryService.insertFailedHistory(lecture, user.id());
        }
    }

    public List<LectureSignedUpResponseDto> getSignedUpLectures(Long userId) {
        User user = userService.getUser(userId);

        List<Long> signedUpLectureIds = lectureApplyHistoryService.findSignedUpLectureHistories(user.id());
        List<Lecture> signedUpLectures = lectureService.findSignedUpLectures(signedUpLectureIds);

        return signedUpLectures.stream()
                .map(LectureSignedUpResponseDto::fromDomain)
                .toList();
    }
}
