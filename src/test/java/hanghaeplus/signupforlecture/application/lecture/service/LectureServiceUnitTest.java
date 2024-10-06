package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecturer;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureRepository;
import hanghaeplus.signupforlecture.application.lecture.validator.LectureValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceUnitTest {

    @InjectMocks
    private LectureService sut;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureValidator lectureValidator;

    @Test
    @DisplayName("요청된 날짜에 해당되는 강의가 없으면 예외발생")
    void fail_applyAvailableSlotTest() {
        // given
        LocalDate requestDate = LocalDate.of(2024, 10, 2);
        when(lectureRepository.findByAvailableDateOrderById(requestDate)).thenReturn(new ArrayList<>());

        // when
        NoSuchElementException exception  = assertThrows(NoSuchElementException.class, () -> {
            sut.getAvailableLectures(requestDate);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("요청한 날짜에 신청 가능한 강의가 없습니다.");
    }

    @Test
    @DisplayName("요청된 날짜에 해당되는 강의 목록 조회")
    void applyAvailableSlotTest() {
        // given
        List<Lecture> givenLectures = new ArrayList<>();
        LocalDate requestDate = LocalDate.of(2024, 10, 1);
        for (int i = 1; i <= 2; i++) {
            Lecture lecture = Lecture.builder()
                    .id((long) i)
                    .title("제목" + i)
                    .lecturer(new Lecturer(1L, "코치1"))
                    .lecturer(null)
                    .availableDate(requestDate)
                    .build();
            givenLectures.add(lecture);
        }
        when(lectureRepository.findByAvailableDateOrderById(requestDate)).thenReturn(givenLectures);

        // when
//        List<Long> results = sut.getAvailableLectures(requestDate);

        // then
//        assertThat(results.get(0).title()).isEqualTo("제목1");
//        assertThat(results.get(1).title()).isEqualTo("제목2");
    }

    @Test
    @DisplayName("강의가 없으면 예외 발생")
    void fail_getLecture() {

        // given
        Long lectureId = 1L;

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        // when
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            sut.getLecture(lectureId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("등록된 강의가 아닙니다.");
    }
}