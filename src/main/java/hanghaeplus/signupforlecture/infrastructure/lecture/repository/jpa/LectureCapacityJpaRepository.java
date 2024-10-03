package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureCapacityEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LectureCapacityJpaRepository extends CrudRepository<LectureCapacityEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lc FROM LectureCapacityEntity lc " +
            "WHERE lc.lectureId = :lectureId " +
            "AND lc.availableSlot > 0 ")
    Optional<LectureCapacityEntity> findByLectureIdGreaterThanZeroLock(@Param("lectureId") Long lectureId);
}
