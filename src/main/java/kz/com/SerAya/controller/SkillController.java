package kz.com.SerAya.controller;


import kz.com.SerAya.dto.SkillDto;
import kz.com.SerAya.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @RequestMapping(value="/skill",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SkillDto>> findAll() {
        return ResponseEntity.ok(skillService.findAll());
    }
    @RequestMapping(value="/skill/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<SkillDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(skillService.findById(id));
    }
    @RequestMapping(value="/programs/skill/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SkillDto>> findSkillsByProgram(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(skillService.findSkillsByProgram(id));
    }
    @RequestMapping(value = "/skill/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        skillService.delete(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/skill",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody SkillDto skill
    ) {
        return ResponseEntity.ok(skillService.save(skill));
    }
    @PutMapping(value="/skill/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody SkillDto skillDto
    ) {
        skillService.update(id, skillDto);
        return ResponseEntity.ok().build();
    }

}
