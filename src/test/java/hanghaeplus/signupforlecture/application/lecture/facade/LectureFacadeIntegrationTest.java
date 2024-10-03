package hanghaeplus.signupforlecture.application.lecture.facade;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecturer;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureApplyHistoryRepository;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureRepository;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LecturerRepository;
import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureApplyRequestDto;
import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.application.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LectureFacadeIntegrationTest {

    @Autowired
    private LectureFacade sut;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private LectureCapacityRepository lectureCapacityRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureApplyHistoryRepository lectureApplyHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // 강사 생성
        Lecturer lecturer = Lecturer.builder()
                .id(1L)
                .name("강사A")
                .build();
        lecturerRepository.save(lecturer);

        // 강의 용량 생성
        LectureCapacity lectureCapacity = LectureCapacity.builder()
                .id(1L)
                .lectureId(1L)
                .maxSlot(30)
                .availableSlot(30)
                .build();
        lectureCapacityRepository.save(lectureCapacity);

        // 강의 생성
        Lecture lecture = Lecture.builder()
                .id(1L)
                .title("강의A")
                .lecturer(lecturer)
                .lectureCapacity(lectureCapacity)
                .availableDate(LocalDate.now())
                .build();
        lectureRepository.save(lecture);

        // 유저 40명 생성
        for (long i = 1; i <= 40; i++) {
            User user = User.builder()
                    .id(i)
                    .name("유저 " + i)
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    @DisplayName("동시에 동일한 특강에 대해 40명이 신청했을 때, 30명 이후에는 예외발생")
    void fail_applyLecture() {
        // given
        Long lectureId = 1L;
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // when
        for (int i = 1; i <= 40; i++) {
            Long userId = (long) i;
            LectureApplyRequestDto lectureApplyRequestDto = LectureApplyRequestDto.builder()
                    .userId(userId)
                    .build();

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                sut.applyLecture(lectureId, lectureApplyRequestDto);
            });
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        RuntimeException exception = assertThrows(RuntimeException.class, allOf::join);

        List<LectureApplyHistory> lectureApplyHistories = lectureApplyHistoryRepository.findByLectureIdAndAppliedStatus(lectureId);

        // then
        assertThat(exception.getMessage()).isEqualTo("신청 가능한 Slot이 없습니다.");
        assertThat(lectureApplyHistories.size()).isEqualTo(30);

    }
}