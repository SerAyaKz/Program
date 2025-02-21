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
@Table(name = "learning_outcomes")
public class LearningOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "learningOutcomeKz", nullable = false, length = 500)
    private String learningOutcomeKz;

    @Column(name = "learningOutcomeRu", nullable = false, length = 500)
    private String learningOutcomeRu;

    @Column(name = "learningOutcomeEn", nullable = false, length = 500)
    private String learningOutcomeEn;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
}
