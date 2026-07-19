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
    private String nameEn;
    private String descriptionEn;
    private String nameRu;
    private String descriptionRu;
    private String nameKz;
    private String descriptionKz;
    private String job_type;
    private Integer programId;

    public static JobDto fromEntity(Job job, Integer programId) {
        if (job == null) {
            return null;
        }

        return JobDto.builder()
                .id(job.getId())
                .nameEn(job.getNameEn())
                .descriptionEn(job.getDescriptionEn())
                .nameRu(job.getNameRu())
                .descriptionRu(job.getDescriptionRu())
                .nameKz(job.getNameKz())
                .descriptionKz(job.getDescriptionKz())
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
                .nameEn(jobDto.getNameEn())
                .descriptionEn(jobDto.getDescriptionEn())
                .nameRu(jobDto.getNameRu())
                .descriptionRu(jobDto.getDescriptionRu())
                .nameKz(jobDto.getNameKz())
                .descriptionKz(jobDto.getDescriptionKz())
                .job_type(jobDto.getJob_type()) // Corrected mapping
                .build();
    }
}
