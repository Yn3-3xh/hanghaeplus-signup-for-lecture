package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LectureJpaRepository extends CrudRepository<LectureEntity, Long> {
    List<LectureEntity> findByIdIn(List<Long> ids);

    @Query("SELECT l FROM LectureEntity l " +
            "JOIN l.lectureCapacityEntity lc " +
            "WHERE l.availableDate = :requestDate " +
            "AND lc.availableSlot > 0")
    List<LectureEntity> getAvailableLectures(@Param("requestDate") LocalDate requestDate);
}