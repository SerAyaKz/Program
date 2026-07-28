package kz.com.SerAya.service; // TODO: adjust to your actual base package


import kz.com.SerAya.dto.JoinRequest;
import kz.com.SerAya.dto.JoinRequestDto;
import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.dto.ProgramUserDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.User;
import kz.com.SerAya.entity.UserProgram;
import kz.com.SerAya.enums.JoinRequestStatus;
import kz.com.SerAya.repository.JoinRequestRepository;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.repository.UserProgramRepository;
import kz.com.SerAya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final UserProgramRepository userProgramRepository;
    private final JoinRequestRepository joinRequestRepository;

    /**
     * All users, each with the list of programs they are currently
     * an approved member of. Used to render the Users page.
     */
    public List<ProgramUserDto> getAllUsersWithPrograms() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    List<ProgramDto> programs = userProgramRepository.findByUserId(user.getId())
                            .stream()
                            .map(UserProgram::getProgram)
                            .map(ProgramDto::fromEntity) // TODO: add fromEntity to ProgramDto if not present
                            .collect(Collectors.toList());
                    return ProgramUserDto.fromEntity(user, programs);
                })
                .collect(Collectors.toList());
    }

    /**
     * Creates a pending join request for a user wanting to join a program.
     * Rejects duplicates: a user cannot have two open PENDING requests
     * for the same program, and cannot request a program they already belong to.
     */
    @Transactional
    public JoinRequestDto requestToJoin(Integer userId, Integer programId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new NoSuchElementException("Program not found: " + programId));

        if (userProgramRepository.existsByUserIdAndProgramId(userId, programId)) {
            throw new IllegalStateException("User is already a member of this program");
        }

        joinRequestRepository.findByUserIdAndProgramIdAndStatus(userId, programId, JoinRequestStatus.PENDING)
                .ifPresent(existing -> {
                    throw new IllegalStateException("A pending request for this program already exists");
                });

        JoinRequest joinRequest = JoinRequest.builder()
                .user(user)
                .program(program)
                .status(JoinRequestStatus.PENDING)
                .requestedDate(LocalDateTime.now())
                .build();

        return JoinRequestDto.fromEntity(joinRequestRepository.save(joinRequest));
    }

    /** Pending requests for a given program, e.g. for an owner/admin to review. */
    public List<JoinRequestDto> getPendingRequestsForProgram(Integer programId) {
        return joinRequestRepository.findByProgramIdAndStatus(programId, JoinRequestStatus.PENDING)
                .stream()
                .map(JoinRequestDto::fromEntity)
                .collect(Collectors.toList());
    }

    /** Approves a request: marks it APPROVED and creates the membership row. */
    @Transactional
    public JoinRequestDto approve(Integer requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Join request not found: " + requestId));

        joinRequest.setStatus(JoinRequestStatus.APPROVED);
        joinRequest.setResolvedDate(LocalDateTime.now());
        joinRequestRepository.save(joinRequest);

        if (!userProgramRepository.existsByUserIdAndProgramId(
                joinRequest.getUser().getId(), joinRequest.getProgram().getId())) {
            UserProgram membership = UserProgram.builder()
                    .user(joinRequest.getUser())
                    .program(joinRequest.getProgram())
                    .joinedDate(LocalDateTime.now())
                    .build();
            userProgramRepository.save(membership);
        }

        return JoinRequestDto.fromEntity(joinRequest);
    }

    /** Rejects a pending request. */
    @Transactional
    public JoinRequestDto reject(Integer requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Join request not found: " + requestId));

        joinRequest.setStatus(JoinRequestStatus.REJECTED);
        joinRequest.setResolvedDate(LocalDateTime.now());
        joinRequestRepository.save(joinRequest);

        return JoinRequestDto.fromEntity(joinRequest);
    }
}