package hanghaeplus.signupforlecture.application.lecture.service;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import hanghaeplus.signupforlecture.application.lecture.domain.repository.LectureCapacityRepository;
import hanghaeplus.signupforlecture.application.lecture.validator.LectureCapacityValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureCapacityServiceUnitTest {

    @InjectMocks
    private LectureCapacityService sut;

    @Mock
    private LectureCapacityRepository lectureCapacityRepository;

    @Mock
    private LectureCapacityValidator lectureCapacityValidator;

    @Test
    @DisplayName("남은 자리가 없으면 예외발생")
    void fail_checkAvailableSlotTest() {
        // given
        Long lectureId = 1L;

        when(lectureCapacityRepository.findByLectureIdGreaterThanZero(lectureId)).thenReturn(Optional.empty());

        // when
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            sut.getAvailableSlot(lectureId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("신청 가능한 Slot이 없습니다.");
    }

}