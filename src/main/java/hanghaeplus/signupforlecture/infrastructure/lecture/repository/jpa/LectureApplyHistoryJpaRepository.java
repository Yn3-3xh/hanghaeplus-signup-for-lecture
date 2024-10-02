package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LectureApplyHistoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LectureApplyHistoryJpaRepository extends CrudRepository<LectureApplyHistoryEntity, Long> {
    List<LectureApplyHistoryEntity> findAllByUserId(Long userId);
}
