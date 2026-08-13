package kz.com.SerAya.dto;

import lombok.Data;

import java.util.List;
@Data
public class CourseOutcomeMappingItem {
    private String nameEn;
    private List<String> learningOutcomeCodes;
    // getters/setters
}