package kz.com.SerAya.controller;


import kz.com.SerAya.dto.JobDto;
import kz.com.SerAya.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @RequestMapping(value="/job",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<JobDto>> findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }
    @RequestMapping(value="/job/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<JobDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(jobService.findById(id));
    }
    @RequestMapping(value="/programs/job/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<JobDto>> findJobsByProgram(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(jobService.findJobsByProgram(id));
    }
    @RequestMapping(value="/job/generate/{id}",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> generate(
            @PathVariable("id") Integer id
    ) {
        jobService.generate(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/job/skills/generate/{id}",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> collectSkills(
            @PathVariable("id") Integer id
    ) {
        jobService.collectSkills(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value = "/job/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        jobService.delete(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/job",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody JobDto job
    ) {
        return ResponseEntity.ok(jobService.save(job));
    }
    @PutMapping(value="/job/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody JobDto jobDto
    ) {
        jobService.update(id, jobDto);
        return ResponseEntity.ok().build();
    }

}
