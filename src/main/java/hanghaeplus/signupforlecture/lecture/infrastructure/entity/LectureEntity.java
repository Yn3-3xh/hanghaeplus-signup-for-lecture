package hanghaeplus.signupforlecture.lecture.infrastructure.entity;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecture")
@Getter
@NoArgsConstructor
public class LectureEntity extends AbstractAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private LecturerEntity lecturerEntity;

    private int maxSlot;

    private int availableSlot;

    private LocalDateTime availableDate;

    public Lecture toDomain() {
        return Lecture.builder()
                .id(this.id)
                .title(this.title)
                .lecturer(this.lecturerEntity.toDomain())
                .maxSlot(this.maxSlot)
                .availableSlot(this.availableSlot)
                .availableDate(this.availableDate)
                .build();
    }

    public static LectureEntity fromDomain(Lecture lecture) {
        LectureEntity lectureEntity = new LectureEntity();
        lectureEntity.id = lecture.getId();
        lectureEntity.title = lecture.getTitle();
        lectureEntity.lecturerEntity = LecturerEntity.fromDomain(lecture.getLecturer());
        lectureEntity.maxSlot = lecture.getMaxSlot();
        lectureEntity.availableSlot = lecture.getAvailableSlot();
        lectureEntity.availableDate = lecture.getAvailableDate();

        return lectureEntity;
    }
}
