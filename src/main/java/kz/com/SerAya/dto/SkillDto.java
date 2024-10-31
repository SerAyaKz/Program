package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Job;
import kz.com.SerAya.entity.Skill;
import kz.com.SerAya.repository.SkillRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {
    private Integer id;
    private String name;
    private int freq; // Frequency of the skill
    private Integer jobId; // Store the job's ID for simplicity

    public static SkillDto fromEntity(Skill skill) {
        if (skill == null) {
            return null;
        }

        return SkillDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .freq(skill.getFreq())
                .jobId(skill.getJob() != null ? skill.getJob().getId() : null) // Get the job ID
                .build();
    }

    public static Skill toEntity(SkillDto skillDto, Job job, SkillRepository skillRepository) {
        if (skillDto == null) {
            return null;
        }

        Skill skill = Skill.builder()
                .id(skillDto.getId() != null ? skillDto.getId() : null)
                .name(skillDto.getName())
                .freq(skillDto.getFreq())
                .job(job) // Set the job entity
                .build();

        return skill;
    }
}
