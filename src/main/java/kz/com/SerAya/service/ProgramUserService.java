package kz.com.SerAya.service;

import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.UserProgram;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.repository.UserProgramRepository;
import kz.com.SerAya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramUserService {

    private final ProgramRepository programRepository;
    private final UserRepository userRepository;
    private final UserProgramRepository programUserRepository;

    public List<ProgramDto> findAllByUserId(Integer userId) {
       return programRepository.findAllByUser(userId)
               .stream()
               .map(ProgramDto::fromEntity)
               .collect(Collectors.toList());
    }

    // Save a new program and link it to the user
    @Transactional
    public Program saveProgramByUserId(ProgramDto programDto, int id) {
        Program program = ProgramDto.toEntity(programDto);
        Program savedProgram = programRepository.save(program);
        UserProgram programUser = new UserProgram();
        programUser.setProgram(savedProgram);
        programUser.setUser(userRepository.findById(id).orElse(null));
        programUser.setJoinedDate(LocalDateTime.now());
        programUserRepository.save(programUser);
        return savedProgram;
    }

    // Delete programs and connections based on array of program IDs
    @Transactional
    public void deleteByUserId(Integer userId, Integer program) {
        programUserRepository.removeProgramFromUser(userId,program);
    }
}
