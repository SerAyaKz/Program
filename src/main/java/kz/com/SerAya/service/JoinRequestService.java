package kz.com.SerAya.service;

import kz.com.SerAya.entity.JoinRequest;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final UserProgramRepository userProgramRepository;
    private final JoinRequestRepository joinRequestRepository;

    public List<ProgramUserDto> getAllUsersWithPrograms() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    List<ProgramDto> programs = userProgramRepository.findByUserId(user.getId())
                            .stream()
                            .map(UserProgram::getProgram)
                            .map(ProgramDto::fromEntity)
                            .collect(Collectors.toList());
                    return ProgramUserDto.fromEntity(user, programs);
                })
                .collect(Collectors.toList());
    }

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

    public List<JoinRequestDto> getPendingRequestsForProgram(Integer programId) {
        return joinRequestRepository.findByProgramIdAndStatus(programId, JoinRequestStatus.PENDING)
                .stream()
                .map(JoinRequestDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * The "owner" of a program is whoever has been a member the longest
     * (earliest joinedDate in user_programs for that program).
     * Program has no getOwner(), so we derive it this way.
     */
    public Integer getProgramOwnerId(Integer programId) {
        return userProgramRepository.findFirstByProgramIdOrderByJoinedDateAsc(programId)
                .map(UserProgram::getUser)
                .map(User::getId)
                .orElseThrow(() -> new NoSuchElementException("No members found for program: " + programId));
    }

    /**
     * All pending join requests for every program this user owns.
     * Used to populate the "Join Requests" dialog for that user.
     */
    public List<JoinRequestDto> getPendingRequestsForOwner(Integer ownerId) {
//        List<UserProgram> memberships = userProgramRepository.findByUserId(ownerId);
//
//        return memberships.stream()
//                .map(UserProgram::getProgram)
//                .filter(program -> ownerId.equals(getProgramOwnerId(program.getId())))
//                .flatMap(program -> joinRequestRepository
//                        .findByProgramIdAndStatus(program.getId(), JoinRequestStatus.PENDING)
//                        .stream())
//                .map(JoinRequestDto::fromEntity)
//                .collect(Collectors.toList());

        List<UserProgram> memberships = userProgramRepository.findByUserId(ownerId);
        System.out.println(memberships.size());
        System.out.println(memberships);
        List<Program> programs = memberships.stream()
                .map(UserProgram::getProgram)
                .collect(Collectors.toList());
        System.out.println(programs.size());
        System.out.println(programs);
        List<Program> ownedPrograms = programs.stream()
                .filter(program -> ownerId.equals(getProgramOwnerId(program.getId())))
                .collect(Collectors.toList());
        System.out.println(ownedPrograms.size());
        System.out.println(ownedPrograms);
        List<JoinRequest> requests = ownedPrograms.stream()
                .flatMap(program ->
                        joinRequestRepository
                                .findByProgramIdAndStatus(
                                        program.getId(),
                                        JoinRequestStatus.PENDING)
                                .stream())
                .collect(Collectors.toList());
        System.out.println(requests.size());
        System.out.println(requests);
        List<JoinRequestDto> result = requests.stream()
                .map(JoinRequestDto::fromEntity)
                .collect(Collectors.toList());
        System.out.println(result);
        return result;
    }

    /** Approves a request — only the program owner may do this. */
    @Transactional
    public JoinRequestDto approve(Integer requestId, Integer approverId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Join request not found: " + requestId));

        Integer ownerId = getProgramOwnerId(joinRequest.getProgram().getId());
        if (!ownerId.equals(approverId)) {
            throw new AccessDeniedException("Only the program owner can approve this request");
        }

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

    /** Rejects a request — only the program owner may do this. */
    @Transactional
    public JoinRequestDto reject(Integer requestId, Integer approverId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Join request not found: " + requestId));

        Integer ownerId = getProgramOwnerId(joinRequest.getProgram().getId());
        if (!ownerId.equals(approverId)) {
            throw new AccessDeniedException("Only the program owner can reject this request");
        }

        joinRequest.setStatus(JoinRequestStatus.REJECTED);
        joinRequest.setResolvedDate(LocalDateTime.now());
        joinRequestRepository.save(joinRequest);

        return JoinRequestDto.fromEntity(joinRequest);
    }

    /**
     * Removes a member from a program. Only the program owner may do this,
     * and the owner cannot remove themselves.
     */
    @Transactional
    public void removeMember(Integer programId, Integer memberId, Integer requesterId) {
        Integer ownerId = getProgramOwnerId(programId);

        if (!ownerId.equals(requesterId)) {
            throw new AccessDeniedException("Only the program owner can remove members");
        }
        if (memberId.equals(ownerId)) {
            throw new IllegalStateException("The program owner cannot remove themselves");
        }

        UserProgram membership = userProgramRepository.findByUserIdAndProgramId(memberId, programId)
                .orElseThrow(() -> new NoSuchElementException("User is not a member of this program"));

        userProgramRepository.delete(membership);
    }

    /** Lists members of a program so the owner can choose who to remove. */
    public List<ProgramUserDto> getProgramMembers(Integer programId) {
        return userProgramRepository.findByProgramId(programId)
                .stream()
                .map(UserProgram::getUser)
                .map(user -> ProgramUserDto.fromEntity(user, List.of()))
                .collect(Collectors.toList());
    }

    public List<ProgramUserDto> getAllUsersWithProgramsByOwner(Integer ownerId) {

        List<User> users = userRepository.findUsersInOwnersPrograms(ownerId);

        List<Integer> ownerProgramIds = userProgramRepository.findOwnersPrograms(ownerId);
        List<Integer> ownerCreatedProgramIds = userProgramRepository.findOnlyOwnersPrograms(ownerId);
// general streaming all users based on owner's programs
        List<ProgramUserDto> sss = users.stream()
                .map(user -> {
                    List<ProgramDto> programs = userProgramRepository.findByUserId(user.getId())
                            .stream()
                            .filter(up -> ownerProgramIds.contains(up.getProgram().getId()))
                            .map(UserProgram::getProgram)
                            .map(ProgramDto::fromEntity)
                            .toList();

                    return ProgramUserDto.fromEntity(user, programs);
                })
                .toList();
        // removing not created by owner programs from owner ProgramUserDto
        for (ProgramUserDto dto : sss) {
            if (Objects.equals(dto.getId(), ownerId)) {
                dto.setPrograms(
                        dto.getPrograms().stream()
                                .filter(program -> ownerCreatedProgramIds.contains(program.getId()))
                                .toList()
                );
            }
        }

        return sss;
    }
}