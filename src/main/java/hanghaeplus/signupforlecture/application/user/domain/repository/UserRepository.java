package hanghaeplus.signupforlecture.application.user.domain.repository;

import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.infrastructure.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findById(Long userId);

    void save(User user);
}
