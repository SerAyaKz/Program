package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Job;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Skill;
import kz.com.SerAya.repository.JobRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private Integer id;
    private String name;
    private String description;
    private Integer program_id; // Store the program's ID for simplicity
    private List<SkillDto> skills = new ArrayList<>(); // List of Skill DTOs

    public static JobDto fromEntity(Job job) {
        if (job == null) {
            return null;
        }

        return JobDto.builder()
                .id(job.getId())
                .name(job.getName())
                .description(job.getDescription())
                .program_id(job.getProgram() != null ? job.getProgram().getId() : null)
                .skills(job.getSkills() != null
                        ? job.getSkills().stream()
                        .map(SkillDto::fromEntity)
                        .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public static Job toEntity(JobDto jobDto, Program program, JobRepository jobRepository) {
        if (jobDto == null) {
            return null;
        }

        Job job = Job.builder()
                .id(jobDto.getId() != null ? jobDto.getId() : null)
                .name(jobDto.getName())
                .description(jobDto.getDescription())
                .program(program)
                .build();

//        // Map skills from DTO to entity if present
//        if (jobDto.getSkills() != null && !jobDto.getSkills().isEmpty()) {
//            Set<Skill> skillEntities = jobDto.getSkills().stream()
//                    .map(skillDto -> SkillDto.toEntity(skillDto, jobRepository))
//                    .collect(Collectors.toSet());
//            job.setSkills(skillEntities);
//        }

        return job;
    }
}
