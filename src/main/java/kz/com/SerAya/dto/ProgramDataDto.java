package kz.com.SerAya.dto;

import kz.com.SerAya.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
            List<CourseProgram> courses,
            List<CourseLearningOutcome> courseLearningOutcomes
    ) {
        Map<Integer, Set<String>> loCodesByCourseId = courseLearningOutcomes.stream()
                .collect(Collectors.groupingBy(
                        clo -> clo.getCourse().getId(),
                        Collectors.mapping(clo -> clo.getLearningOutcome().getCode(), Collectors.toSet())
                ));
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
                        .map(cp -> CourseProgramDto.fromEntity(cp, loCodesByCourseId))
                        .toList())
                .build();
    }
}
