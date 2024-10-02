package hanghaeplus.signupforlecture.lecture.application.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecturer;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureRepository;
import hanghaeplus.signupforlecture.application.lecture.service.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceImplTest {

    @InjectMocks
    private LectureService sut;

    @Mock
    private LectureRepository lectureRepository;

    List<Lecture> givenLectures = new ArrayList<>();

    @BeforeEach
    void setUp() {
        LocalDate baseDate = LocalDate.of(2024, 10, 1);
        for (int i = 1; i <= 2; i++) {
            LocalDate lectureDate = baseDate.plusDays(i);
            Lecture lecture = Lecture.builder()
                    .id((long) i)
                    .title("제목" + 1)
                    .lecturer(new Lecturer(1L, "코치1"))
                    .lecturer(null)
                    .availableDate(lectureDate)
                    .build();
            givenLectures.add(lecture);
            lectureRepository.save(lecture);
        }
    }

    @Test
    @DisplayName("요청된 날짜에 신청 가능한 강의가 있는지 확인")
    void applyAvailableSlotTest() {
        // given
        LocalDate requestDate = LocalDate.of(2024, 10, 2);
        List<Lecture> savedLectures = new ArrayList<>();
        savedLectures.add(givenLectures.get(1));

        when(lectureRepository.findByAvailableDate(requestDate))
                .thenReturn(savedLectures);

        // when
        List<Lecture> results = sut.findAvailableLectures(requestDate);

        // then
        assertThat(results).isEqualTo(savedLectures);
    }
}