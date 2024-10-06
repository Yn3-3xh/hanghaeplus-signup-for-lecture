package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecture;
import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureCapacityEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureCapacityJpaRepository extends CrudRepository<LectureCapacityEntity, Long> {

    List<LectureCapacityEntity> findByLectureIdInOrderByLectureId(List<Long> lectureIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.query.timeout", value = "5000")})
    @Query("SELECT lc FROM LectureCapacityEntity lc " +
            "WHERE lc.lectureId = :lectureId " +
            "AND lc.availableSlot > 0 ")
    Optional<LectureCapacityEntity> findByLectureIdGreaterThanZeroLock(@Param("lectureId") Long lectureId);

//    @Override
//    @Query("UPDATE ")
//    void save(LectureCapacityEntity lectureCapacity)
}
