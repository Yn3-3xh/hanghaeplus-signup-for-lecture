package hanghaeplus.signupforlecture.infrastructure.user.entity;

import hanghaeplus.signupforlecture.application.user.domain.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public User toDomain() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static UserEntity fromDomain(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.id();
        userEntity.name = user.name();

        return userEntity;
    }
}