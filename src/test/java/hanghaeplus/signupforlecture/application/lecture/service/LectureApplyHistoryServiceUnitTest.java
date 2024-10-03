package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.application.lecture.domain.model.enums.ApplyStatus;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureApplyHistoryRepository;
import hanghaeplus.signupforlecture.application.lecture.validator.LectureApplyHistoryValidator;
import hanghaeplus.signupforlecture.application.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureApplyHistoryServiceUnitTest {

    @InjectMocks
    private LectureApplyHistoryService sut;

    @Mock
    private LectureApplyHistoryRepository lectureApplyHistoryRepository;

    @Mock
    private LectureApplyHistoryValidator lectureApplyHistoryValidator;

    @Test
    @DisplayName("강의 중복 신청 시 예외발생")
    void fail_applyLectureDuplicationTest() {
        // given
        Long lectureId = 1L;
        Long userId = 1L;
        User user = new User(userId, "USER1");
        Lecture givenLecture = Lecture.builder()
                .id(lectureId)
                .title("")
                .lecturer(null)
                .lectureCapacity(null)
                .availableDate(null)
                .build();
        LectureApplyHistory givenLectureApplyHistory = LectureApplyHistory.builder()
                .id(1L)
                .lecture(givenLecture)
                .userId(userId)
                .applyStatus(ApplyStatus.APPLIED)
                .build();
        Optional<LectureApplyHistory> lectureApplyHistory = Optional.of(givenLectureApplyHistory);

        when(lectureApplyHistoryRepository.findByLectureIdAndUserIdAndApplyStatus(lectureId, user.id()))
                .thenReturn(lectureApplyHistory);

        // when
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            sut.checkApplyLectureHistory(lectureId, userId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("해당 강의에 이미 신청한 이력이 있습니다.");
    }
}