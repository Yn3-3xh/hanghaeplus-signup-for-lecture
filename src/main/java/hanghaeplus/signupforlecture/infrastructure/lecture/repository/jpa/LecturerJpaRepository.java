package hanghaeplus.signupforlecture.infrastructure.lecture.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.lecture.entity.LecturerEntity;
import org.springframework.data.repository.CrudRepository;

public interface LecturerJpaRepository extends CrudRepository<LecturerEntity, Long> {
}
