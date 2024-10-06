package hanghaeplus.signupforlecture.infrastructure.lecture.entity;

import hanghaeplus.signupforlecture.application.lecture.domain.model.LectureApplyHistory;
import hanghaeplus.signupforlecture.application.lecture.domain.model.enums.ApplyStatus;
import hanghaeplus.signupforlecture.infrastructure.lecture.entity.enums.ApplyStatusEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture_apply_history")
@Getter
@NoArgsConstructor
public class LectureApplyHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private LectureEntity lectureEntity;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private ApplyStatusEntity applyStatusEntity;

    public LectureApplyHistory toDomain() {
        return LectureApplyHistory.builder()
                .id(this.id)
                .lecture(this.lectureEntity.toDomain())
                .userId(this.userId)
                .applyStatus(ApplyStatus.valueOf(this.applyStatusEntity.name()))
                .build();
    }

    public static LectureApplyHistoryEntity fromDomain(LectureApplyHistory lectureApplyHistory) {
        LectureApplyHistoryEntity lectureApplyHistoryEntity = new LectureApplyHistoryEntity();
        lectureApplyHistoryEntity.id = lectureApplyHistory.id();
        lectureApplyHistoryEntity.lectureEntity = LectureEntity.fromDomain(lectureApplyHistory.lecture());
        lectureApplyHistoryEntity.userId = lectureApplyHistory.userId();
        lectureApplyHistoryEntity.applyStatusEntity = ApplyStatusEntity.valueOf(lectureApplyHistory.applyStatus().name());

        return lectureApplyHistoryEntity;
    }
}
