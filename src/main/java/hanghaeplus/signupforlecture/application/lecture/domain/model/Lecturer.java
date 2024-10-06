package hanghaeplus.signupforlecture.application.lecture.domain.model;

import lombok.Builder;

@Builder
public record Lecturer (

    Long id,
    String name
) {
}
