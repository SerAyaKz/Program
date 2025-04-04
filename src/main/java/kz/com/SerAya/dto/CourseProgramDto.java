package kz.com.SerAya.dto;

import kz.com.SerAya.entity.CourseProgram;
import kz.com.SerAya.entity.LearningOutcome;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
    private Integer courseId;
    private Set<Integer> learningOutcomeIds;

    public static CourseProgramDto fromEntity(CourseProgram courseProgram) {
        if (courseProgram == null) {
            return null;
        }
        return CourseProgramDto.builder()
                .id(courseProgram.getId())
                .year(courseProgram.getYear())
                .term(courseProgram.getTerm())
                .creditCount(courseProgram.getCreditCount())
                .programId(courseProgram.getProgram() != null ? courseProgram.getProgram().getId() : null)
                .courseId(courseProgram.getCourse() != null ? courseProgram.getCourse().getId() : null)
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
