package kz.com.SerAya.controller;

import kz.com.SerAya.dto.CourseLearningOutcomeRequestDto;
import kz.com.SerAya.dto.CourseProgramDto;
import kz.com.SerAya.dto.StandardDto;
import kz.com.SerAya.entity.CourseProgram;
import kz.com.SerAya.service.CourseProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseProgramController {
    private final CourseProgramService courseProgramService;

    @RequestMapping(value="/courseProgram",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<CourseProgramDto>> findAll() {
        return ResponseEntity.ok(courseProgramService.findAll());
    }
    @RequestMapping(value="/courseProgram/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<CourseProgramDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(courseProgramService.findById(id));
    }

    @RequestMapping(value = "/courseProgram/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        courseProgramService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/programs/courseProgram/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<CourseProgramDto>> findCourseProgramsByProgram(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(courseProgramService.findCourseProgramsByProgram(id));
    }
    
    @RequestMapping(value="/courseProgram",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody CourseProgramDto courseProgramDto
    ) {

        return ResponseEntity.ok(courseProgramService.save(courseProgramDto));
    }
    @PutMapping(value="/courseProgram/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody CourseProgramDto courseProgramDto
    ) {
        courseProgramService.update(id, courseProgramDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/courseLearningOutcome/add")
    public ResponseEntity<Void> addOutcomes(@RequestBody CourseLearningOutcomeRequestDto request) {
        courseProgramService.addLearningOutcomes(
                request.getCourseId(),
                request.getProgramId(),
                request.getLearningOutcomeCodes()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/courseLearningOutcome/remove")
    public ResponseEntity<Void> removeOutcomes(@RequestBody CourseLearningOutcomeRequestDto request) {
        courseProgramService.removeLearningOutcomes(
                request.getCourseId(),
                request.getProgramId(),
                request.getLearningOutcomeCodes()
        );
        return ResponseEntity.ok().build();
    }
}
