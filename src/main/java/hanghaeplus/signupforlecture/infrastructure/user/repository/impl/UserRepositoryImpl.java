package hanghaeplus.signupforlecture.infrastructure.user.repository.impl;

import hanghaeplus.signupforlecture.application.user.domain.model.User;
import hanghaeplus.signupforlecture.application.user.domain.repository.UserRepository;
import hanghaeplus.signupforlecture.infrastructure.user.entity.UserEntity;
import hanghaeplus.signupforlecture.infrastructure.user.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId)
                .map(UserEntity::toDomain);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(UserEntity.fromDomain(user));
    }
}
