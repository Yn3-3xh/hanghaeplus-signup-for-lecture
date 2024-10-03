package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureCapacityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LectureCapacityJpaRepository extends CrudRepository<LectureCapacityEntity, Long> {

    @Query("SELECT lc FROM LectureCapacityEntity lc " +
            "WHERE lc.availableSlot > 0 ")
    Optional<LectureCapacityEntity> findByLectureIdGreaterThanZero(Long lectureId);
}
