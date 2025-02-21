package kz.com.SerAya.service.impl;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.entity.User;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.repository.CourseRepository;
import kz.com.SerAya.repository.UserRepository;
import kz.com.SerAya.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final UserRepository userRepository;

    @Override
    public Integer save(CourseDto dto) {
        Set<User> users = dto.getUserIds() != null ?
                (Set<User>) userRepository.findAllById(dto.getUserIds()) :
                Collections.emptySet();

        Course course = CourseDto.toEntity(dto, users);
        Course savedCourse = repository.save(course);

        return savedCourse.getId();
    }

    @Override
    public List<CourseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(CourseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto findById(Integer id) {
        return repository.findById(id)
                .map(CourseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No course found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, CourseDto courseDto) {
        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No course found with the ID: " + id));

        existingCourse.setCode(courseDto.getCode());
        existingCourse.setNameKz(courseDto.getNameKz());
        existingCourse.setNameRu(courseDto.getNameRu());
        existingCourse.setNameEn(courseDto.getNameEn());
        existingCourse.setBriefInfoKz(courseDto.getBriefInfoKz());
        existingCourse.setBriefInfoRu(courseDto.getBriefInfoRu());
        existingCourse.setBriefInfoEn(courseDto.getBriefInfoEn());
        existingCourse.setSelective(courseDto.isSelective());

        repository.save(existingCourse);
    }

    public Course saveCourse(CourseDto dto) {
        Set<User> users = dto.getUserIds() != null ?
                (Set<User>) userRepository.findAllById(dto.getUserIds()) :
                Collections.emptySet();

        Course course = CourseDto.toEntity(dto, users);
        return repository.save(course);
    }

}
