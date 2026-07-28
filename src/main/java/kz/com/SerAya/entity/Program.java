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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codeName", nullable = true)
    private String codeName;

    @Column(name = "academicDegree", nullable = true)
    private String academicDegree;

    @Column(name = "eduGoalKz", nullable = true)
    private String eduGoalKz;

    @Column(name = "eduGoalRu", nullable = true)
    private String eduGoalRu;

    @Column(name = "eduGoalEn", nullable = true)
    private String eduGoalEn;

    @Column(name = "direction_code_name", nullable = true)
    private String directionCodeName;

    @Column(name = "isced_level", nullable = true)
    private Integer iscedLevel;

    @Column(name = "nqf_level", nullable = true)
    private int nqfLevel;

    @Column(name = "sqf_level", nullable = true)
    private int sqfLevel;

    @Column(name = "study_duration_years", nullable = true)
    private int studyDurationYears;

    @Column(name = "creditsCount", nullable = true)
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



}