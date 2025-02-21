package kz.com.SerAya.controller;


import kz.com.SerAya.dto.RecommendationDto;
import kz.com.SerAya.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @RequestMapping(value="/recommendation",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<RecommendationDto>> findAll() {
        return ResponseEntity.ok(recommendationService.findAll());
    }

    @RequestMapping(value="/recommendation/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<RecommendationDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(recommendationService.findById(id));
    }

    @RequestMapping(value = "/recommendation/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        recommendationService.delete(id);
        return ResponseEntity.accepted().build();
    }
    
    @RequestMapping(value="/recommendation",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(
            @RequestBody RecommendationDto recommendation
    ) {
        return ResponseEntity.ok(recommendationService.save(recommendation));
    }
}
