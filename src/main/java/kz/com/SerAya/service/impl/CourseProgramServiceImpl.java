package kz.com.SerAya.service.impl;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.CourseProgramDto;
import kz.com.SerAya.dto.JobDto;
import kz.com.SerAya.entity.CourseProgram;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.entity.LearningOutcome;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.repository.CourseProgramRepository;
import kz.com.SerAya.repository.CourseRepository;
import kz.com.SerAya.repository.LearningOutcomeRepository;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.service.CourseProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseProgramServiceImpl implements CourseProgramService {

    private final CourseProgramRepository repository;
    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final LearningOutcomeRepository learningOutcomeRepository;


    @Override
    public List<CourseProgramDto> findAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @Override
    public CourseProgramDto findById(Integer id) {
        Optional<CourseProgram> courseProgram = repository.findById(id);
        return courseProgram.map(this::convertToDTO).orElse(null);
    }
    @Override
    public Integer save(CourseProgramDto dto) {
        CourseProgram courseProgram = convertToEntity(dto);
        courseProgram = repository.save(courseProgram);
        return courseProgram.getId();
    }
    @Override
    public void update(Integer id, CourseProgramDto dto) {
        if (!repository.existsById(id)) {
        }
        CourseProgram courseProgram = convertToEntity(dto);
        courseProgram.setId(id);
        repository.save(courseProgram);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private CourseProgramDto convertToDTO(CourseProgram courseProgram) {
        CourseProgramDto dto = new CourseProgramDto();
        dto.setId(courseProgram.getId());
        dto.setYear(courseProgram.getYear());
        dto.setTerm(courseProgram.getTerm());
        dto.setCreditCount(courseProgram.getCreditCount());
        dto.setProgramId(courseProgram.getProgram().getId());
        CourseDto courseDto=  courseRepository.findById(courseProgram.getCourse().getId()).map(CourseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No course found with the ID : " + courseProgram.getCourse().getId()));;
        dto.setCourse(courseDto);
        return dto;
    }

    private CourseProgram convertToEntity(CourseProgramDto dto) {
        CourseProgram courseProgram = new CourseProgram();
        courseProgram.setYear(dto.getYear());
        courseProgram.setTerm(dto.getTerm());
        courseProgram.setCreditCount(dto.getCreditCount());

        Program program = programRepository.findById(dto.getProgramId()).orElseThrow(() -> new RuntimeException("Program not found"));
        courseProgram.setProgram(program);

        Course course = courseRepository.findById(dto.getCourse().getId()).orElseThrow(() -> new RuntimeException("Course not found"));
        courseProgram.setCourse(course);

        return courseProgram;
    }
    public CourseProgramDto convertToDto(CourseProgram courseProgram) {
        CourseProgramDto dto = new CourseProgramDto();
        dto.setId(courseProgram.getId());
        dto.setYear(courseProgram.getYear());
        dto.setTerm(courseProgram.getTerm());
        dto.setCreditCount(courseProgram.getCreditCount());

        if (courseProgram.getProgram() != null) {
            dto.setProgramId(courseProgram.getProgram().getId());
        }

        if (courseProgram.getCourse() != null) {
            dto.setCourse(courseRepository.findById(courseProgram.getCourse().getId()).map(CourseDto::fromEntity)
                    .orElseThrow(() -> new EntityNotFoundException("No course found with the ID : " + courseProgram.getCourse().getId())));
        }

        return dto;
    }



    public List<CourseProgramDto> findCourseProgramsByProgram(Integer id) {
        List<CourseProgram> coursePrograms = repository.findCourseProgramsByProgram(id);
        return coursePrograms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
