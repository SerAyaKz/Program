package kz.com.SerAya.dto;

import kz.com.SerAya.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProgramDataDto {

    private ProgramDto program;

    private List<JobDto> jobs;

    private List<StandardDto> standards;

    private List<LearningOutcomeDto> outcomes;

    private List<CourseProgramDto> courses;

    public static ProgramDataDto fromEntity(
            Program program,
            List<Job> jobs,
            List<Standard> standards,
            List<LearningOutcome> outcomes,
            List<CourseProgram> courses
    ) {

        return ProgramDataDto.builder()
                .program(ProgramDto.fromEntity(program))
                .jobs(jobs.stream()
                        .map(job -> JobDto.fromEntity(job, program.getId()))
                        .toList())
                .standards(standards.stream()
                        .map(standard -> StandardDto.fromEntity(standard, program.getId()))
                        .toList())
                .outcomes(outcomes.stream()
                        .map(LearningOutcomeDto::fromEntity)
                        .toList())
                .courses(courses.stream()
                        .map(CourseProgramDto::fromEntity)
                        .toList())
                .build();
    }
}
