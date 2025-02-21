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
    private String jobType; // Updated field name to match entity
    private Integer programId;

    public static JobDto fromEntity(Job job) {
        if (job == null) {
            return null;
        }

        return JobDto.builder()
                .id(job.getId())
                .name(job.getName())
                .description(job.getDescription())
                .jobType(job.getJob_type()) // Ensure correct mapping
                .programId(job.getProgram() != null ? job.getProgram().getId() : null)
                .build();
    }

    public static Job toEntity(JobDto jobDto, Program program) {
        if (jobDto == null) {
            return null;
        }

        return Job.builder()
                .id(jobDto.getId())
                .name(jobDto.getName())
                .description(jobDto.getDescription())
                .job_type(jobDto.getJobType()) // Corrected mapping
                .program(program)
                .build();
    }
}
