package hanghaeplus.signupforlecture.application.user.domain.model;

import lombok.Builder;

@Builder
public record User (

    Long id,
    String name
) {
}
