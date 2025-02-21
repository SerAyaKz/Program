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
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private String briefInfoKz;
    private String briefInfoRu;
    private String briefInfoEn;
    private boolean isSelective;

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
                .build();
    }

    public static Course toEntity(CourseDto courseDto) {
        if (courseDto == null) {
            return null;
        }

        return Course.builder()
                .id(courseDto.getId() != null ? courseDto.getId() : null)
                .code(courseDto.getCode())
                .nameKz(courseDto.getNameKz())
                .nameRu(courseDto.getNameRu())
                .nameEn(courseDto.getNameEn())
                .briefInfoKz(courseDto.getBriefInfoKz())
                .briefInfoRu(courseDto.getBriefInfoRu())
                .briefInfoEn(courseDto.getBriefInfoEn())
                .isSelective(courseDto.isSelective())
                .build();
    }
}
