package hanghaeplus.signupforlecture.lecture.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Lecture {

    private Long id;

    private String title;

    private Lecturer lecturer;

    private int maxSlot;

    private int availableSlot;

    private LocalDateTime availableDate;

    @Builder
    public Lecture(Long id,
                   String title,
                   Lecturer lecturer,
                   int maxSlot,
                   int availableSlot,
                   LocalDateTime availableDate) {
        this.id = id;
        this.title = title;
        this.lecturer = lecturer;
        this.maxSlot = maxSlot;
        this.availableSlot = availableSlot;
        this.availableDate = availableDate;
    }

}
