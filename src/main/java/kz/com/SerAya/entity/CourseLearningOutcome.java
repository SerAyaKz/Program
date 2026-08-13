package kz.com.SerAya.entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_learning_outcome",
        uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "learning_outcome_id"}))
public class CourseLearningOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "learning_outcome_id", referencedColumnName = "id", nullable = false)
    private LearningOutcome learningOutcome;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
}