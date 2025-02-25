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
    private String job_type;
    private Integer programId;

    public static JobDto fromEntity(Job job, Integer programId) {
        if (job == null) {
            return null;
        }

        return JobDto.builder()
                .id(job.getId())
                .name(job.getName())
                .description(job.getDescription())
                .job_type(job.getJob_type()) // Ensure correct mapping
                .programId(programId)
                .build();
    }

    public static Job toEntity(JobDto jobDto) {
        if (jobDto == null) {
            return null;
        }

        return Job.builder()
                .id(jobDto.getId())
                .name(jobDto.getName())
                .description(jobDto.getDescription())
                .job_type(jobDto.getJob_type()) // Corrected mapping
                .build();
    }
}
