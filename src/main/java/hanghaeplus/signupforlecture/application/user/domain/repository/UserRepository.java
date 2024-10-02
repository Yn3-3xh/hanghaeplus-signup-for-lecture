package hanghaeplus.signupforlecture.application.user.domain.repository;

import hanghaeplus.signupforlecture.application.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long userId);

    void save(User user);
}
