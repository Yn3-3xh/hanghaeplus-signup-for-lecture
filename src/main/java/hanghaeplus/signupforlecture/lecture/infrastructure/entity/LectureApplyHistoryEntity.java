package hanghaeplus.signupforlecture.lecture.infrastructure.entity;

import hanghaeplus.signupforlecture.lecture.domain.model.ApplyStatus;
import hanghaeplus.signupforlecture.lecture.domain.model.LectureApplyHistory;
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
        lectureApplyHistoryEntity.id = lectureApplyHistory.getId();
        lectureApplyHistoryEntity.lectureEntity = LectureEntity.fromDomain(lectureApplyHistory.getLecture());
        lectureApplyHistoryEntity.userId = lectureApplyHistory.getUserId();
        lectureApplyHistoryEntity.applyStatusEntity = ApplyStatusEntity.valueOf(lectureApplyHistory.getApplyStatus().name());

        return lectureApplyHistoryEntity;
    }
}
