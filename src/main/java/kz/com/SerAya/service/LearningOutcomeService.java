package kz.com.SerAya.service;

import kz.com.SerAya.dto.LearningOutcomeDto;

import java.util.List;

public interface LearningOutcomeService extends AbstractService<LearningOutcomeDto> {
    List<LearningOutcomeDto> findLearningOutcomesByProgram(Integer id);
    void generate(Integer id);
}
