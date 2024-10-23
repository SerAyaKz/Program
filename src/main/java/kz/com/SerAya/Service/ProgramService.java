package kz.com.SerAya.Service;

import kz.com.SerAya.DTO.ProgramDto;
import kz.com.SerAya.Entity.Program;

import java.util.List;

public interface ProgramService extends AbstractService<ProgramDto> {
    List<Program> findAllPrograms(); // Method to return List<Program> directly
    Program findProgramById(Integer id);
}
