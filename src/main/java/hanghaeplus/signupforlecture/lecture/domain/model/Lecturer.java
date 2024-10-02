package hanghaeplus.signupforlecture.lecture.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Lecturer {

    private Long id;

    private String name;

    @Builder
    public Lecturer(Long id,
                    String name) {
        this.id = id;
        this.name = name;
    }
}
