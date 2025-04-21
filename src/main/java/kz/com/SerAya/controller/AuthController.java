package kz.com.SerAya.controller;

import kz.com.SerAya.dto.UserDto;
import kz.com.SerAya.entity.User;
import kz.com.SerAya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.createOrUpdateUser(userDto);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error registering user: " + e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<?> getUserByUid(@PathVariable String uid) {
        return userService.findByUid(uid)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

}