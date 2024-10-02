package hanghaeplus.signupforlecture.lecture.infrastructure.repository.jpa;

import hanghaeplus.signupforlecture.lecture.infrastructure.entity.LecturerEntity;
import hanghaeplus.signupforlecture.lecture.infrastructure.repository.LecturerDataRepository;
import org.springframework.data.repository.CrudRepository;

public interface LecturerJpaRepository extends CrudRepository<LecturerEntity, Long>, LecturerDataRepository {
}
