package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Course;
import kz.com.SerAya.entity.User;
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
public class CourseDto {
    private Integer id;
    private String code;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private String briefInfoKz;
    private String briefInfoRu;
    private String briefInfoEn;
    private boolean isSelective;
    private Set<Integer> userIds; // Storing only user IDs to avoid circular dependency

    public static CourseDto fromEntity(Course course) {
        if (course == null) {
            return null;
        }

        return CourseDto.builder()
                .id(course.getId())
                .code(course.getCode())
                .nameKz(course.getNameKz())
                .nameRu(course.getNameRu())
                .nameEn(course.getNameEn())
                .briefInfoKz(course.getBriefInfoKz())
                .briefInfoRu(course.getBriefInfoRu())
                .briefInfoEn(course.getBriefInfoEn())
                .isSelective(course.isSelective())
                .userIds(course.getUsers() != null ?
                        course.getUsers().stream().map(User::getId).collect(Collectors.toSet()) : null)
                .build();
    }

    public static Course toEntity(CourseDto courseDto, Set<User> users) {
        if (courseDto == null) {
            return null;
        }

        return Course.builder()
                .id(courseDto.getId())
                .code(courseDto.getCode())
                .nameKz(courseDto.getNameKz())
                .nameRu(courseDto.getNameRu())
                .nameEn(courseDto.getNameEn())
                .briefInfoKz(courseDto.getBriefInfoKz())
                .briefInfoRu(courseDto.getBriefInfoRu())
                .briefInfoEn(courseDto.getBriefInfoEn())
                .isSelective(courseDto.isSelective())
                .users(users) // Assigning users directly
                .build();
    }
}
