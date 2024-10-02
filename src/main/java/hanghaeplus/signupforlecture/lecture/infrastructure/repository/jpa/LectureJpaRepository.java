package hanghaeplus.signupforlecture.lecture.infrastructure.repository.jpa;

import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LectureEntity;
import hanghaeplus.signupforlecture.lecture.infrastructure.repository.LectureDataRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LectureJpaRepository extends CrudRepository<LectureEntity, Long>, LectureDataRepository {
    List<LectureEntity> findByIdIn(List<Long> ids);

    @Query("SELECT l FROM LectureEntity l WHERE l.availableSlot > 0")
    List<LectureEntity> findAvailableLectures();
}
