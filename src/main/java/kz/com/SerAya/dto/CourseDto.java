package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Integer id;
    private String code;
    private String name;
    private String description;
    private String cycle;
    private String type;

    public static CourseDto fromEntity(Course course) {
        if (course == null) {
            return null;
        }

        return CourseDto.builder()
                .id(course.getId())
                .code(course.getCode())
                .name(course.getName())
                .description(course.getDescription())
                .cycle(course.getCycle())
                .type(course.getType())
                .build();
    }

    public static Course toEntity(CourseDto courseDto) {
        if (courseDto == null) {
            return null;
        }

        return Course.builder()
                .id(courseDto.getId() != null ? courseDto.getId() : null)
                .code(courseDto.getCode())
                .name(courseDto.getName())
                .description(courseDto.getDescription())
                .cycle(courseDto.getCycle())
                .type(courseDto.getType())
                .build();
    }
}
