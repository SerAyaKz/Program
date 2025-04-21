package kz.com.SerAya.service;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.entity.CourseUser;
import kz.com.SerAya.entity.User;
import kz.com.SerAya.repository.CourseRepository;
import kz.com.SerAya.repository.CourseUserRepository;
import kz.com.SerAya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseUserService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseUserRepository courseUserRepository;

    public List<CourseDto> findAllByUserId(Integer userId) {
       return courseRepository.findAllByUser(userId)
               .stream()
               .map(CourseDto::fromEntity)
               .collect(Collectors.toList());
    }

    // Save a new course and link it to the user
    @Transactional
    public Course saveCourseByUserId(CourseDto courseDto, int id) {
        Course course = CourseDto.toEntity(courseDto);
        Course savedCourse = courseRepository.save(course);
        CourseUser courseUser = new CourseUser();
        courseUser.setCourse(savedCourse);
        courseUser.setUser(userRepository.findById(id).orElse(null));
        courseUser.setAssignedAt(LocalDateTime.now());
        courseUserRepository.save(courseUser);
        return savedCourse;
    }

    // Delete courses and connections based on array of course IDs
    @Transactional
    public void deleteByUserId(Integer userId, Integer course) {
        courseUserRepository.removeCourseFromUser(userId,course);
    }
}
