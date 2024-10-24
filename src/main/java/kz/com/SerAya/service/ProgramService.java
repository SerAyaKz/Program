package kz.com.SerAya.service;

import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.entity.Program;

import java.util.List;

public interface ProgramService extends AbstractService<ProgramDto> {
    List<Program> findAllPrograms(); // Method to return List<Program> directly
    Program findProgramById(Integer id);
}
