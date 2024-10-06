package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LectureJpaRepository extends CrudRepository<LectureEntity, Long> {
    List<LectureEntity> findByIdIn(List<Long> ids);

    List<LectureEntity> findByAvailableDateOrderById(LocalDate requestDate);
}