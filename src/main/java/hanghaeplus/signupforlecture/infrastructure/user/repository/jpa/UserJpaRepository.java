package hanghaeplus.signupforlecture.infrastructure.user.repository.jpa;

import hanghaeplus.signupforlecture.infrastructure.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
}
