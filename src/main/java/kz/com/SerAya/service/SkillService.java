package kz.com.SerAya.service;

import kz.com.SerAya.dto.SkillDto;

import java.util.List;

public interface SkillService extends AbstractService<SkillDto> {
    List<SkillDto> findSkillsByProgram(Integer id);
}
