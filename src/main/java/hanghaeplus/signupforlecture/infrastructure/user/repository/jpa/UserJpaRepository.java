package hanghaeplus.signupforlecture.infrastructure.user.repository.jpa;

import hanghaeplus.signupforlecture.application.user.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<User, Long> {
}
