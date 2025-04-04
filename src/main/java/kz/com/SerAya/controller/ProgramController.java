package kz.com.SerAya.controller;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
