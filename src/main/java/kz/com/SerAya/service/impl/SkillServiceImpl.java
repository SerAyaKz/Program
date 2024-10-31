package kz.com.SerAya.service.impl;


import kz.com.SerAya.dto.SkillDto;
import kz.com.SerAya.entity.Skill;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.repository.SkillRepository;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final ProgramRepository programRepository;
    private final SkillRepository repository;

    @Override
    public Integer save(SkillDto dto) {
//        Program program = programRepository.findById(dto.getProgram_id()).orElseThrow(EntityNotFoundException::new);
//        Skill skill = SkillDto.toEntity(dto,program);
//
//        Skill savedSkill = repository.save(skill);
//
//
//        return savedSkill.getId();
        return null;
    }

    @Override
    public List<SkillDto> findAll() {
        return repository.findAll()
                .stream()
                .map(SkillDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public SkillDto findById(Integer id) {
        return repository.findById(id)
                .map(SkillDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

    @Override
    public List<SkillDto> findSkillsByProgram(Integer id) {
        return repository.findSkillsByProgram(id).stream()
                .map(SkillDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void update(Integer id, SkillDto skillDto) {

        Skill existingSkill = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No skill found with the ID: " + id));

        existingSkill.setName(skillDto.getName());

        repository.save(existingSkill);
    }
}
