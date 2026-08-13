package kz.com.SerAya.dto;

import kz.com.SerAya.entity.CourseProgram;
import kz.com.SerAya.entity.LearningOutcome;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CourseProgramDto {
    private Integer id;
    private Integer year;
    private Integer term;
    private Integer creditCount;
    private Integer programId;
    private CourseDto course;
    private Set<String> learningOutcomeCodes;

    public static CourseProgramDto fromEntity(CourseProgram courseProgram, Map<Integer, Set<String>> loCodesByCourseId) {
        Integer courseId = courseProgram.getCourse().getId();
        if (courseProgram == null) {
            return null;
        }
        return CourseProgramDto.builder()
                .id(courseProgram.getId())
                .year(courseProgram.getYear())
                .term(courseProgram.getTerm())
                .creditCount(courseProgram.getCreditCount())
                .programId(courseProgram.getProgram() != null ? courseProgram.getProgram().getId() : null)
                .course(courseProgram.getCourse() != null
                        ? CourseDto.fromEntity(courseProgram.getCourse())
                        : null)
                .learningOutcomeCodes(loCodesByCourseId.getOrDefault(courseId, Collections.emptySet()))
                .build();
    }

    public static CourseProgram toEntity(CourseProgramDto dto, Program program, Course course, Set<LearningOutcome> learningOutcomes) {
        if (dto == null) {
            return null;
        }
        return CourseProgram.builder()
                .id(dto.getId())
                .year(dto.getYear())
                .term(dto.getTerm())
                .creditCount(dto.getCreditCount())
                .program(program)
                .course(course)
                .build();
    }
}
