package kz.com.SerAya.service;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.CourseProgramDto;
import kz.com.SerAya.dto.JobDto;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.entity.CourseProgram;

import java.util.List;

public interface CourseProgramService extends AbstractService<CourseProgramDto> {
    List<CourseProgramDto> findCourseProgramsByProgram(Integer id);

    void addLearningOutcomes(Integer courseId, Integer programId, List<String> learningOutcomeCodes);

    void removeLearningOutcomes(Integer courseId, Integer programId, List<String> learningOutcomeCodes);
}
