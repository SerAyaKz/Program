package kz.com.SerAya.controller;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.ProgramUserDto;
import kz.com.SerAya.dto.UserDto;
import kz.com.SerAya.entity.Course;
import kz.com.SerAya.service.CourseUserService;
import kz.com.SerAya.service.JoinRequestService;
import kz.com.SerAya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JoinRequestService joinRequestService;

    @RequestMapping(value="/api/users",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<ProgramUserDto>> getAllUsers() {
        return ResponseEntity.ok(joinRequestService.getAllUsersWithPrograms());
    }
    @RequestMapping(value="/api/user/{id}", method=RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<UserDto> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(userService.findById(id));
    }


}
