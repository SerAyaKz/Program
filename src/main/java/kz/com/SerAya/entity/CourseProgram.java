package kz.com.SerAya.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_program")
public class CourseProgram{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "term")
    private Integer term;

    @Column(name = "creditCount")
    private Integer creditCount;

    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id", nullable = false)
    private Program program;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "learningOutcome", referencedColumnName = "id", nullable = false)
    private LearningOutcome learningOutcome;

}