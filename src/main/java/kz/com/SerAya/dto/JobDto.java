package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Job;
import kz.com.SerAya.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private Integer id;
    private String name;
    private String description;

    private String job_skill;
    private Integer program_id;


    public static JobDto fromEntity(Job job) {
        if (job == null) {
            return null;
        }

        return JobDto.builder()
                .id(job.getId())
                .name(job.getName())
                .description(job.getDescription())
                .job_skill(job.getJob_skill())
                .program_id(job.getProgram() != null ? job.getProgram().getId() : null)
                .build();
    }

    public static Job toEntity(JobDto jobDto, Program program) {
        if (jobDto == null) {
            return null;
        }

        Job job = Job.builder()
                .id(jobDto.getId() != null ? jobDto.getId() : null)
                .name(jobDto.getName())
                .description(jobDto.getDescription())
                .job_skill(jobDto.getJob_skill())
                .program(program)
                .build();

        return job;
    }
}
