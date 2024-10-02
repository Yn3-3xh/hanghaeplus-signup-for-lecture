package hanghaeplus.signupforlecture.lecture.infrastructure.repository.jpa;

import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LectureApplyHistoryEntity;
import hanghaeplus.signupforlecture.lecture.infrastructure.repository.LectureApplyHistoryDataRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LectureApplyHistoryJpaRepository extends CrudRepository<LectureApplyHistoryEntity, Long>, LectureApplyHistoryDataRepository {
    List<LectureApplyHistoryEntity> findAllByUserId(Long userId);
}
