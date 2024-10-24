package kz.com.SerAya.controller;

import kz.com.SerAya.dto.SectionDto;
import kz.com.SerAya.entity.Section;
import kz.com.SerAya.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @RequestMapping(value="/section",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SectionDto>> findAll() {
        return ResponseEntity.ok(sectionService.findAll());
    }
    @RequestMapping(value="/section/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<SectionDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(sectionService.findById(id));
    }
    @RequestMapping(value="/programs/section/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<Section>> findSectionsByProgram(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(sectionService.findSectionsByProgram(id));
    }
    @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        sectionService.delete(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/section",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody SectionDto sectionDto
    ) {

        return ResponseEntity.ok(sectionService.save(sectionDto));
    }
    @PutMapping(value="/section/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody SectionDto sectionDto
    ) {
        sectionService.update(id, sectionDto);
        return ResponseEntity.ok().build();
    }
}
