package hanghaeplus.signupforlecture.application.lecture.facade;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecturer;
import hanghaeplus.signupforlecture.application.lecture.domain.model.enums.ApplyStatus;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureApplyHistoryRepository;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureRepository;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LecturerRepository;
import hanghaeplus.signupforlecture.application.lecture.dto.request.LectureApplyRequestDto;
import hanghaeplus.signupforlecture.application.lecture.service.LectureCapacityService;
import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.application.user.domain.repository.UserRepository;
import jakarta.persistence.PessimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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


    @Autowired
    private LectureCapacityService lectureCapacityService;

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
                .lectureCapacityId(1L)
//                .lectureCapacity(lectureCapacity)
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
            }).exceptionally(e -> {
                System.out.println("예외 발생: " + e.getMessage());
                return null; // 예외 발생 시 null 반환
            });
            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        // then
        Optional<LectureCapacity> resultCapacity = lectureCapacityRepository.findById(1L);
        assertThat(resultCapacity.get().availableSlot()).isEqualTo(0);

        List<LectureApplyHistory> lectureApplyHistories = lectureApplyHistoryRepository.findByLectureIdAndAppliedStatus(lectureId);
        assertThat(lectureApplyHistories.size()).isEqualTo(30);

        List<LectureApplyHistory> lectureFailedHistories = lectureApplyHistoryRepository.findByLectureIdAndFailedStatus(lectureId);
        assertThat(lectureFailedHistories.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("동시에 동일한 특강에 대해 40명이 신청했을 때, 30명 이후에는 예외발생")
    void fail_applyLecture2() throws InterruptedException {
        // given
        Long lectureId = 1L;
        int numberOfApplicants = 40;
        CountDownLatch latch = new CountDownLatch(numberOfApplicants);
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        List<LectureApplyHistory> lectureApplyHistories = new ArrayList<>();

        // when
        for (int i = 1; i <= numberOfApplicants; i++) {
            Long userId = (long) i;
            LectureApplyRequestDto lectureApplyRequestDto = LectureApplyRequestDto.builder()
                    .userId(userId)
                    .build();

            executorService.execute(() -> {
                try {
                    sut.applyLecture(lectureId, lectureApplyRequestDto);
                } catch (RuntimeException e) {
                    // 예외 발생 시 처리 (로그 또는 상태 저장 등)
                    System.out.println("예외 발생: " + e.getMessage());
                } finally {
                    latch.countDown(); // 모든 스레드가 완료되었음을 알림
                }
            });
        }

        latch.await(); // 모든 신청이 완료될 때까지 대기
        executorService.shutdown(); // ExecutorService 종료

        // then
        lectureApplyHistories = lectureApplyHistoryRepository.findByLectureIdAndAppliedStatus(lectureId);
        assertThat(lectureApplyHistories.size()).isEqualTo(30); // 신청한 강의 수 검증
    }

    @Test
    @DisplayName("한 유저가 같은 강의에 5번 신청한 경우 예외발생")
    void fail_applyLecture3() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 강사, 강의, 신청 내역 생성 (H2는 gap lock이 없기 때문)
        Lecturer lecturer = Lecturer.builder()
                .id(1L)
                .name("강사A")
                .build();
        Lecture lecture = Lecture.builder()
                .id(lectureId)
                .title("강의A")
                .lecturer(lecturer)
                .lectureCapacityId(1L)
//                .lectureCapacity(lectureCapacity)
                .availableDate(LocalDate.now())
                .build();
        LectureApplyHistory lectureApplyHistory = LectureApplyHistory.builder()
                .id(1L)
                .lecture(lecture)
                .userId(userId)
                .applyStatus(ApplyStatus.APPLIED)
                .build();
        lectureApplyHistoryRepository.save(lectureApplyHistory);

        // when
        for (int i = 1; i <= 5; i++) {
            LectureApplyRequestDto lectureApplyRequestDto = LectureApplyRequestDto.builder()
                    .userId(userId)
                    .build();

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                sut.applyLecture(lectureId, lectureApplyRequestDto);
            }).exceptionally(e -> {
                System.out.println("예외 발생: " + e.getMessage());
                return null; // 예외 발생 시 null 반환
            });
            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        // then
        List<LectureApplyHistory> lectureApplyHistories = lectureApplyHistoryRepository.findByLectureIdAndAppliedStatus(lectureId);
        assertThat(lectureApplyHistories.size()).isEqualTo(1);

        List<LectureApplyHistory> lectureFailedHistories = lectureApplyHistoryRepository.findByLectureIdAndFailedStatus(lectureId);
        assertThat(lectureFailedHistories.size()).isEqualTo(5);
    }
}