package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {
    private Integer id;
    private String name;
    private int freq;
    private int yearRange;
    private LocalDateTime createdDate;
    private Integer programId;
}
