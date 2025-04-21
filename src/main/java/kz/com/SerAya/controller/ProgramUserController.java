package kz.com.SerAya.controller;

import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.service.ProgramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProgramUserController {
    private final ProgramUserService programUserService;

    @RequestMapping(value="/program/user/{id}",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<ProgramDto>> findAll(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(programUserService.findAllByUserId(id));
    }

    @RequestMapping(value = "/program/user/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id,
   @RequestBody Integer programIdsToDelete
    ) {
        programUserService.deleteByUserId(id, programIdsToDelete);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/program/user/{id}",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Program> save(
            @RequestBody ProgramDto programDto, @PathVariable("id") Integer id
    ) {

        return ResponseEntity.ok(programUserService.saveProgramByUserId(programDto,id));
    }

}
