package kz.com.SerAya.controller;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.service.CourseService;
import kz.com.SerAya.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseUserController {
    private final CourseUserService courseUserService;

    @RequestMapping(value="/course/user/{id}",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<CourseDto>> findAll(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(courseUserService.findAllByUserId(id));
    }

    @RequestMapping(value = "/course/user/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id,
   @RequestBody Integer courseIdsToDelete
    ) {
        courseUserService.deleteByUserId(id, courseIdsToDelete);
        return ResponseEntity.accepted().build();
    }
    @RequestMapping(value="/course/user/{id}",method=RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Course> save(
            @RequestBody CourseDto courseDto, @PathVariable("id") Integer id
    ) {

        return ResponseEntity.ok(courseUserService.saveCourseByUserId(courseDto,id));
    }

}
