package kz.com.SerAya.Controller;


import kz.com.SerAya.DTO.StandardDto;
import kz.com.SerAya.Entity.Standard;
import kz.com.SerAya.Service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StandardController {
    private final StandardService standardService;

    @RequestMapping(value="/standard",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<StandardDto>> findAll() {
        return ResponseEntity.ok(standardService.findAll());
    }
    @RequestMapping(value="/standard/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<StandardDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(standardService.findById(id));
    }
    @RequestMapping(value="/programs/standard/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<StandardDto>> findStandardsByProgram(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(standardService.findStandardsByProgram(id));
    }
    @RequestMapping(value = "/standard/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        standardService.delete(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/standard",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody StandardDto standard
    ) {
        return ResponseEntity.ok(standardService.save(standard));
    }
    @PutMapping(value="/standard/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody StandardDto standardDto
    ) {
        standardService.update(id, standardDto);
        return ResponseEntity.ok().build();
    }

}
