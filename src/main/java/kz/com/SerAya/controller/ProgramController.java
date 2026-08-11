package kz.com.SerAya.controller;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.ProgramDataDto;
import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @RequestMapping(value="/program",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<Program>> findAll() {


        return ResponseEntity.ok(programService.findAllPrograms());
    }

    @RequestMapping(value="/program/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Program> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(programService.findProgramById(id));
    }

    @RequestMapping(value = "/program/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        programService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/program",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody ProgramDto programDto
    ) {
        return ResponseEntity.ok(programService.save(programDto));
    }

    @PutMapping(value="/program/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody ProgramDto programDto
    ) {
        programService.update(id, programDto);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value="/dashboard", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        return ResponseEntity.ok(programService.getDashboardData());
    }

    @RequestMapping(value="/program/data/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<ProgramDataDto> findDataById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(programService.findProgramDataById(id));
    }

    @RequestMapping(value="/program/generate/goal/{id}", method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generateGoal(
            @PathVariable("id") Integer id
    ) {
        programService.generateGoal(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/program/generate/recommendation/{id}", method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generateRecommendation(
            @PathVariable("id") Integer id
    ) {
        programService.generateRecommendation(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/program/generate/standard/{id}", method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generateStandard(
            @PathVariable("id") Integer id
    ) {
        programService.generateStandard(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/program/generate/job/{id}", method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generateJob(
            @PathVariable("id") Integer id
    ) {
        programService.generateJob(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/program/generate/course_description/{id}", method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generateCourseDescription(
            @PathVariable("id") Integer id
    ) {
        programService.generateCourseDescription(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/program/generate/outcome/{id}", method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generateOutcome(
            @PathVariable("id") Integer id
    ) {
        programService.generateOutcome(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value="/program/generate/courseProgram/{id}", method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generateCourseProgram(
            @PathVariable("id") Integer id
    ) {
        programService.generateCourseProgram(id);
        return ResponseEntity.accepted().build();
    }

}
