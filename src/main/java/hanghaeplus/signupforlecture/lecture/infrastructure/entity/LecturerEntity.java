package hanghaeplus.signupforlecture.lecture.infrastructure.entity;

import hanghaeplus.signupforlecture.lecture.domain.model.Lecturer;
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

    @OneToMany(mappedBy = "lecturerEntity", fetch = FetchType.LAZY)
    private List<LectureEntity> lectureEntities;

    public Lecturer toDomain() {
        return Lecturer.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static LecturerEntity fromDomain(Lecturer lecturer) {
        LecturerEntity lecturerEntity = new LecturerEntity();
        lecturerEntity.id = lecturer.getId();
        lecturerEntity.name = lecturer.getName();

        return lecturerEntity;
    }
}
