package kz.com.SerAya.controller;

import kz.com.SerAya.dto.JoinRequestDto;
import kz.com.SerAya.dto.ProgramUserDto;
import kz.com.SerAya.service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    @RequestMapping(value="/user/{id}",method= RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<ProgramUserDto>> getAllUsers() {
        return ResponseEntity.ok(joinRequestService.getAllUsersWithPrograms());
    }
    @RequestMapping(value="/api/join-requests",method= RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<JoinRequestDto> requestToJoin(@Valid @RequestBody JoinRequestDto request) {
        JoinRequestDto created = joinRequestService.requestToJoin(request.getUserId(), request.getProgramId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/join-requests/pending?programId=2
    @RequestMapping(value="/api/join-requests/pending",method= RequestMethod.GET, headers = "Accept=application/json")
    public List<JoinRequestDto> getPending(@RequestParam Integer programId) {
        return joinRequestService.getPendingRequestsForProgram(programId);
    }

    // PATCH /api/join-requests/{id}/approve
    @RequestMapping(value="/api/join-requests/{id}/approve",method= RequestMethod.PATCH, headers = "Accept=application/json")
    public JoinRequestDto approve(@PathVariable Integer id) {
        return joinRequestService.approve(id);
    }

    // PATCH /api/join-requests/{id}/reject
    @RequestMapping(value="/api/join-requests/{id}/reject",method= RequestMethod.PATCH, headers = "Accept=application/json")
    public JoinRequestDto reject(@PathVariable Integer id) {
        return joinRequestService.reject(id);
    }

    // Basic error handling so duplicate/invalid requests return sensible HTTP codes
    // instead of a generic 500. Wire this to your app's global exception handler
    // if you already have one, rather than duplicating it here.
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleConflict(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(java.util.NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(java.util.NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

}
