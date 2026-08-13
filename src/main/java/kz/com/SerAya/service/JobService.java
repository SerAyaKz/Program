package kz.com.SerAya.service;

import kz.com.SerAya.dto.JobDto;

import java.util.List;

public interface JobService extends AbstractService<JobDto> {
    List<JobDto> findJobsByProgram(Integer id);
    void generate(Integer id);
//    void collectSkills(Integer id);
}
