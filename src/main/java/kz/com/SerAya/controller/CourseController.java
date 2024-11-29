package kz.com.SerAya.controller;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @RequestMapping(value="/course",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<CourseDto>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }
    @RequestMapping(value="/course/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<CourseDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        courseService.delete(id);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/course",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Course> save(
            @RequestBody CourseDto courseDto
    ) {

        return ResponseEntity.ok(courseService.saveCourse(courseDto));
    }
    @PutMapping(value="/course/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody CourseDto courseDto
    ) {
        courseService.update(id, courseDto);
        return ResponseEntity.ok().build();
    }
}
