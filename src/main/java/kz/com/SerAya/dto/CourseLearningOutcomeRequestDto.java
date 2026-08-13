package kz.com.SerAya.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseLearningOutcomeRequestDto {
    private Integer courseId;
    private Integer programId;
    private List<String> learningOutcomeCodes;
    // getters/setters
}