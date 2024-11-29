package kz.com.SerAya.service;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.SectionDto;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.entity.Section;

import java.util.List;

public interface CourseService extends AbstractService<CourseDto> {
    Course saveCourse(CourseDto dto);
}
