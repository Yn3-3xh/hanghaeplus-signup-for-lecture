package hanghaeplus.signupforlecture.lecture.application.facade;

import hanghaeplus.signupforlecture.lecture.api.dto.LectureApplyRequestDto;
import hanghaeplus.signupforlecture.lecture.api.dto.LectureResponseDto;
import hanghaeplus.signupforlecture.lecture.api.dto.request.LectureAvailableRequestDto;
import hanghaeplus.signupforlecture.lecture.application.service.LectureApplyHistoryService;
import hanghaeplus.signupforlecture.lecture.application.service.LectureService;
import hanghaeplus.signupforlecture.lecture.domain.model.LectureApplyHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final LectureApplyHistoryService lectureApplyHistoryService;

    public List<LectureResponseDto> getAvailableLectures(LectureAvailableRequestDto lectureAvailableRequestDto) {

        return lectureService.findAvailableLectures(lectureAvailableRequestDto.requestDate()).stream()
                .map(LectureResponseDto::fromDomain)
                .toList();
    }

    public List<LectureResponseDto> getSignedUpLectures(Long userId) {

        List<LectureApplyHistory> lectureApplyHistories = lectureApplyHistoryService.findSignedUpLectureHistories(userId);
        List<Long> signedUpLectureIds = lectureApplyHistories.stream()
                .map(lectureApplyHistory -> lectureApplyHistory.getLecture().getId())
                .toList();

        return lectureService.findSignedUpLectures(signedUpLectureIds).stream()
                .map(LectureResponseDto::fromDomain)
                .toList();
    }

    @Transactional
    public void applyLecture(Long lectureId, LectureApplyRequestDto lectureApplyRequestDto) {
        /*
        * lectureId, userId 강의 신청
        * 
        * (Lock)
        * 1. lectureId + userId 중복 체크 (History - 신청,실패,대기)
        * 
            2. 해당 강의 availableSlot > 0 (동시성 + 30명 초과 예외처리) (Lecture)
                > 해당 강의 availableSlot - 1

          3. 강의 신청 내역에 저장 (History)
        * */
        lectureService.applyLecture(lectureId, lectureApplyRequestDto.userId());
    }
}
