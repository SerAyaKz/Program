package kz.com.SerAya.controller;


import kz.com.SerAya.dto.LearningOutcomeDto;
import kz.com.SerAya.service.LearningOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LearningOutcomeController {
    private final LearningOutcomeService learningOutcomeService;

    @RequestMapping(value="/learningOutcome",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<LearningOutcomeDto>> findAll() {
        return ResponseEntity.ok(learningOutcomeService.findAll());
    }
    @RequestMapping(value="/learningOutcome/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<LearningOutcomeDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(learningOutcomeService.findById(id));
    }
    @RequestMapping(value="/programs/learningOutcome/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<LearningOutcomeDto>> findLearningOutcomesByProgram(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(learningOutcomeService.findLearningOutcomesByProgram(id));
    }
    @RequestMapping(value="/learningOutcome/generate/{id}",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generate(
            @PathVariable("id") Integer id
    ) {
        learningOutcomeService.generate(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/learningOutcome/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        learningOutcomeService.delete(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/learningOutcome",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody LearningOutcomeDto learningOutcome
    ) {
        return ResponseEntity.ok(learningOutcomeService.save(learningOutcome));
    }
    @PutMapping(value="/learningOutcome/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody LearningOutcomeDto learningOutcomeDto
    ) {
        learningOutcomeService.update(id, learningOutcomeDto);
        return ResponseEntity.ok().build();
    }

}
