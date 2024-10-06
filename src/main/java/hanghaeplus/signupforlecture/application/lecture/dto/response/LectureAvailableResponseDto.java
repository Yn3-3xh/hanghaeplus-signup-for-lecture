package hanghaeplus.signupforlecture.application.lecture.dto.response;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public record LectureAvailableResponseDto(
    Long lectureId,
    String title,
    String lecturerName,
    LocalDate availableDate,
    int maxSlot,
    int availableSlot
) {
    public static List<LectureAvailableResponseDto> fromDomains(List<Lecture> lectures, List<LectureCapacity> lectureCapacities) {
        return IntStream.range(0, lectures.size())
                .mapToObj(i ->
                        new LectureAvailableResponseDto(
                                lectures.get(i).id(),
                                lectures.get(i).title(),
                                lectures.get(i).lecturer().name(),
                                lectures.get(i).availableDate(),
                                lectureCapacities.get(i).maxSlot(),
                                lectureCapacities.get(i).availableSlot()
                        )
                )
                .toList();
    }
}
