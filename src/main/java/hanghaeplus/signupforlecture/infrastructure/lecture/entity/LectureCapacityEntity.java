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
    private Long lectureId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "lecture_id")
    private LectureEntity lectureEntity;

    private int maxSlot;

    private int availableSlot;

    public LectureCapacity toDomain() {
        return LectureCapacity.builder()
                .lectureId(lectureId)
                .maxSlot(maxSlot)
                .availableSlot(availableSlot)
                .build();
    }

    public static LectureCapacityEntity fromDomain(LectureCapacity lectureCapacity) {
        LectureCapacityEntity lectureCapacityEntity = new LectureCapacityEntity();
        lectureCapacityEntity.lectureId = lectureCapacity.lectureId();
        lectureCapacityEntity.lectureEntity = LectureEntity.fromDomain(lectureCapacity.lecture());
        lectureCapacityEntity.maxSlot = lectureCapacity.maxSlot();
        lectureCapacityEntity.availableSlot = lectureCapacity.availableSlot();

        return lectureCapacityEntity;
    }
}
