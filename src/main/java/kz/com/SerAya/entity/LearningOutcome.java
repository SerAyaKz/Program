package kz.com.SerAya.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "code")
    private String code;

    @Column(name = "learningOutcomeKz",  length = 500)
    private String learningOutcomeKz;

    @Column(name = "learningOutcomeRu", length = 500)
    private String learningOutcomeRu;

    @JsonProperty("learningOutcomeNameEn")
    @Column(name = "learningOutcomeEn", length = 500)
    private String learningOutcomeEn;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
}
