package kz.com.SerAya.Controller;

import kz.com.SerAya.DTO.ProgramDto;
import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Service.ProgramService;
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

}
