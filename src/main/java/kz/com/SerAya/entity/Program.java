package kz.com.SerAya.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "education_field_code", nullable = false)
    private String educationFieldCode;

    @Column(name = "education_field_name", nullable = false)
    private String educationFieldName;

    @Column(name = "training_direction_code", nullable = false)
    private String trainingDirectionCode;

    @Column(name = "training_direction_name", nullable = false)
    private String trainingDirectionName;

    @Column(name = "program_group", nullable = false)
    private String programGroup;

    @Column(name = "isced_level", nullable = false)
    private int iscedLevel;

    @Column(name = "nqf_level", nullable = false)
    private int nqfLevel;

    @Column(name = "sqf_level", nullable = false)
    private int sqfLevel;

    @Column(name = "study_duration_years", nullable = false)
    private int studyDurationYears;

    @Column(name = "credits", nullable = false)
    private int credits;

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

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @OneToMany(mappedBy = "program")
    private Set<ProgramJob> educationProgramJobs;

    @OneToMany
    @JoinTable(
            name = "program_standard",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "standard_id")
    )
    @JsonIgnore
    private Set<Standard> standards;


}