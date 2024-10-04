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
//        allOf.join();
        RuntimeException exception = assertThrows(RuntimeException.class, allOf::join);

        List<LectureApplyHistory> lectureApplyHistories = lectureApplyHistoryRepository.findByLectureIdAndAppliedStatus(lectureId);
        Optional<LectureCapacity> resultCapacity = lectureCapacityRepository.findById(1L);


        // then
        assertThat(exception.getCause().getMessage()).isEqualTo("신청 가능한 Slot이 없습니다.");
        assertThat(lectureApplyHistories.size()).isEqualTo(30);
//        assertThat(lectureApplyHistories.size()).isEqualTo(2);
//        assertThat(resultCapacity.get().availableSlot()).isEqualTo(28);

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
        assertThat(lectureApplyHistories.size()).isEqualTo(30); // 신청한 역사 수 검증
    }

    @Test
    public void testLectureCapacityLock() throws InterruptedException, ExecutionException {
        // 성공과 실패 카운터
        AtomicInteger lockSuccessCount = new AtomicInteger(0);
        AtomicInteger lockFailureCount = new AtomicInteger(0);

        // 스레드 풀 설정
        ExecutorService executorService = Executors.newFixedThreadPool(40);

        // 40명의 유저를 시뮬레이션할 Future 리스트
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        Long lectureId = 1L;  // 테스트할 lectureId 설정

        LectureCapacity lectureCapacity = LectureCapacity.builder()
                .id(1L)
                .lectureId(1L)
                .maxSlot(30)
                .availableSlot(30)
                .build();

        // 40명의 유저가 동시에 락을 시도
        for (int i = 0; i < 40; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    // 락을 획득 시도
                    lectureCapacityService.applyAvailableSlot(lectureCapacity);

                    // 락 획득 성공 시 카운트 증가 및 로그
                    lockSuccessCount.incrementAndGet();
                    System.out.println("Thread " + Thread.currentThread().getId() +
                            " successfully acquired lock on lecture capacity for lectureId: " + lectureId);
                } catch (PessimisticLockException e) {
                    // 락 획득 실패 시 카운트 증가 및 로그
                    lockFailureCount.incrementAndGet();
                    System.err.println("Thread " + Thread.currentThread().getId() +
                            " failed to acquire lock on lecture capacity for lectureId: " + lectureId);
                } catch (Exception e) {
                    System.err.println("Thread " + Thread.currentThread().getId() +
                            " encountered an unexpected error: " + e.getMessage());
                }
            }, executorService);
            futures.add(future);
        }

        // 모든 작업 완료를 기다림
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 성공과 실패 로그 출력
        System.out.println("Total lock successes: " + lockSuccessCount.get());
        System.out.println("Total lock failures: " + lockFailureCount.get());

        // 테스트 결과 검증
        // 성공 또는 실패 카운트에 대해 원하는 조건을 추가
        // 예를 들어, 성공이 1개 이상이어야 한다고 검증
        assert lockSuccessCount.get() > 0 : "At least one lock should be acquired";
        assert lockFailureCount.get() > 0 : "Some locks should fail";
    }
}