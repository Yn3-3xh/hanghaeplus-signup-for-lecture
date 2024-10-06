package hanghaeplus.signupforlecture.infrastructure.lecture.entity;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureCapacity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture_capacity")
@Getter
@NoArgsConstructor
public class LectureCapacityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;

    private int maxSlot;

    private int availableSlot;

    public LectureCapacity toDomain() {
        return LectureCapacity.builder()
                .id(id)
                .lectureId(lectureId)
                .maxSlot(maxSlot)
                .availableSlot(availableSlot)
                .build();
    }

    public static LectureCapacityEntity fromDomain(LectureCapacity lectureCapacity) {
        LectureCapacityEntity lectureCapacityEntity = new LectureCapacityEntity();
        lectureCapacityEntity.id = lectureCapacity.id();
        lectureCapacityEntity.lectureId = lectureCapacity.lectureId();
        lectureCapacityEntity.maxSlot = lectureCapacity.maxSlot();
        lectureCapacityEntity.availableSlot = lectureCapacity.availableSlot();

        return lectureCapacityEntity;
    }
}
