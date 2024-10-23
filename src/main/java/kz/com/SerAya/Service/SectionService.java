package kz.com.SerAya.Service;

import kz.com.SerAya.DTO.ProgramDto;
import kz.com.SerAya.DTO.SectionDto;
import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.Section;

import java.util.List;

public interface SectionService extends AbstractService<SectionDto> {
    List<Section> findSectionsByProgram(Integer id);
}
