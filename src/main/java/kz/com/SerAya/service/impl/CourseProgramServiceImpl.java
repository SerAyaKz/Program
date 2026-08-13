package kz.com.SerAya.service.impl;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.CourseProgramDto;
import kz.com.SerAya.dto.JobDto;
import kz.com.SerAya.entity.*;
import kz.com.SerAya.repository.*;
import kz.com.SerAya.service.CourseProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CourseLearningOutcomeRepository courseLearningOutcomeRepository;

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

    @Transactional
    public void addLearningOutcomes(Integer courseId, Integer programId, List<String> codes) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + courseId));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException("Program not found: " + programId));

        List<LearningOutcome> matchedOutcomes =
                learningOutcomeRepository.findByProgram_IdAndCodeIn(programId, codes);

        Set<String> matchedCodes = matchedOutcomes.stream()
                .map(LearningOutcome::getCode)
                .collect(Collectors.toSet());

        List<String> unmatched = codes.stream()
                .filter(code -> !matchedCodes.contains(code))
                .toList();
        if (!unmatched.isEmpty()) {
            System.out.println("No matching learning outcomes found for codes: " + unmatched);
        }

        Set<String> existingCodes = courseLearningOutcomeRepository
                .findByCourse_IdAndLearningOutcome_CodeIn(courseId, codes)
                .stream()
                .map(clo -> clo.getLearningOutcome().getCode())
                .collect(Collectors.toSet());

        List<CourseLearningOutcome> toSave = matchedOutcomes.stream()
                .filter(lo -> !existingCodes.contains(lo.getCode()))
                .map(lo -> {
                    CourseLearningOutcome clo = new CourseLearningOutcome();
                    clo.setCourse(course);
                    clo.setLearningOutcome(lo);
                    clo.setProgram(program);
                    return clo;
                })
                .toList();

        courseLearningOutcomeRepository.saveAll(toSave);
    }
    @Transactional
    public void removeLearningOutcomes(Integer courseId, Integer programId, List<String> codes) {
        List<CourseLearningOutcome> toDelete =
                courseLearningOutcomeRepository.findByCourse_IdAndLearningOutcome_CodeIn(courseId, codes);

        courseLearningOutcomeRepository.deleteAll(toDelete);
    }
}
