package hanghaeplus.signupforlecture.infrastructure.lecture.entity;

import hanghaeplus.signupforlecture.application.lecture.domain.model.Lecturer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "lecturer")
@Getter
@NoArgsConstructor
public class LecturerEntity extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Lecturer toDomain() {
        return Lecturer.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static LecturerEntity fromDomain(Lecturer lecturer) {
        LecturerEntity lecturerEntity = new LecturerEntity();
        lecturerEntity.id = lecturer.id();
        lecturerEntity.name = lecturer.name();

        return lecturerEntity;
    }
}
