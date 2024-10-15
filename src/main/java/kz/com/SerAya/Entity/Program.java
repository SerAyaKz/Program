package kz.com.SerAya.Entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
@Table(name = "programs")
public class Program extends AbstractEntity {

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
    private Set<Standard> standards;
}