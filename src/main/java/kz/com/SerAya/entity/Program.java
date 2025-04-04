package kz.com.SerAya.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "codeName", nullable = false)
    private String codeName;

    @Column(name = "academicDegree", nullable = false)
    private String academicDegree;

    @Column(name = "eduGoalKz", nullable = false)
    private String eduGoalKz;

    @Column(name = "eduGoalRu", nullable = false)
    private String eduGoalRu;

    @Column(name = "eduGoalEn", nullable = false)
    private String eduGoalEn;

    @Column(name = "direction_code_name", nullable = false)
    private String directionCodeName;

    @Column(name = "isced_level", nullable = true)
    private Integer iscedLevel;

    @Column(name = "nqf_level", nullable = false)
    private int nqfLevel;

    @Column(name = "sqf_level", nullable = false)
    private int sqfLevel;

    @Column(name = "study_duration_years", nullable = false)
    private int studyDurationYears;

    @Column(name = "creditsCount", nullable = false)
    private int creditsCount;

    @CreatedDate
    @Column(
            name = "createdDate",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdDate;

    @CreatedDate
    @Column(
            name = "modifiedDate",
            nullable = false,
            updatable = false
    )
    private LocalDateTime modifiedDate;

    @ManyToMany
    @JoinTable(
            name = "program_job",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private Set<Job> jobs;

    @ManyToMany
    @JoinTable(
            name = "program_standard",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "standard_id")
    )
    private Set<Standard> standards;

    @ManyToMany
    @JoinTable(
            name = "program_user",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

}