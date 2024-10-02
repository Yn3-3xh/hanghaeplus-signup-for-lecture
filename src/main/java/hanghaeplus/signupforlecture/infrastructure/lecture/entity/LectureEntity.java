package hanghaeplus.signupforlecture.infrastructure.lecture.entity;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @OneToOne(mappedBy = "lectureEntity", cascade = CascadeType.ALL)
    private LectureCapacityEntity lectureCapacityEntity;

    private LocalDate availableDate;

    public Lecture toDomain() {
        return Lecture.builder()
                .id(this.id)
                .title(this.title)
                .lecturer(this.lecturerEntity.toDomain())
                .lectureCapacity(this.lectureCapacityEntity.toDomain())
                .availableDate(this.availableDate)
                .build();
    }

    public static LectureEntity fromDomain(Lecture lecture) {
        LectureEntity lectureEntity = new LectureEntity();
        lectureEntity.id = lecture.id();
        lectureEntity.title = lecture.title();
        lectureEntity.lecturerEntity = LecturerEntity.fromDomain(lecture.lecturer());
        lectureEntity.lectureCapacityEntity = LectureCapacityEntity.fromDomain(lecture.lectureCapacity());
        lectureEntity.availableDate = lecture.availableDate();

        return lectureEntity;
    }
}
