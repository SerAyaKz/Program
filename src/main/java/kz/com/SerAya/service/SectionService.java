package kz.com.SerAya.service;

import kz.com.SerAya.dto.SectionDto;
import kz.com.SerAya.entity.Section;

import java.util.List;

public interface SectionService extends AbstractService<SectionDto> {
    List<Section> findSectionsByProgram(Integer id);
}
