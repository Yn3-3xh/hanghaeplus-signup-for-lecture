package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureApplyHistoryEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureApplyHistoryJpaRepository extends CrudRepository<LectureApplyHistoryEntity, Long> {
    List<LectureApplyHistoryEntity> getAllByUserId(Long userId);

    @Query("SELECT lah FROM LectureApplyHistoryEntity lah " +
            "JOIN lah.lectureEntity l " +
            "WHERE l.id = :lectureId " +
            "AND lah.applyStatusEntity = 'APPLIED'")
    List<LectureApplyHistoryEntity> findByLectureIdAndAppliedStatus(@Param("lectureId") Long lectureId);

    @Query("SELECT lah FROM LectureApplyHistoryEntity lah " +
            "JOIN lah.lectureEntity l " +
            "WHERE l.id = :lectureId " +
            "AND lah.applyStatusEntity = 'FAILED'")
    List<LectureApplyHistoryEntity> findByLectureIdAndFailedStatus(@Param("lectureId") Long lectureId);
}
