package kz.com.SerAya.controller;

import kz.com.SerAya.dto.SkillDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Skill;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ProgramRepository programRepository;

    @GetMapping("/program/{programId}")
    public ResponseEntity<List<SkillDto>> getSkillsByProgram(@PathVariable Integer programId) {
        List<SkillDto> dtos = skillRepository
                .findByProgramIdOrderByYearRangeDescFreqDesc(programId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto dto) {
        Program program = programRepository.findById(dto.getProgramId())
                .orElseThrow(() -> new RuntimeException("Program not found: " + dto.getProgramId()));

        Skill skill = new Skill();
        skill.setName(dto.getName());
        skill.setFreq(dto.getFreq());
        skill.setYearRange(dto.getYearRange());
        skill.setProgram(program);

        return ResponseEntity.ok(toDto(skillRepository.save(skill)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable Integer id, @RequestBody SkillDto dto) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found: " + id));

        skill.setName(dto.getName());
        skill.setFreq(dto.getFreq());
        skill.setYearRange(dto.getYearRange());

        return ResponseEntity.ok(toDto(skillRepository.save(skill)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Integer id) {
        skillRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private SkillDto toDto(Skill skill) {
        SkillDto dto = new SkillDto();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        dto.setFreq(skill.getFreq());
        dto.setYearRange(skill.getYearRange());
        dto.setCreatedDate(skill.getCreatedDate());
        dto.setProgramId(skill.getProgram().getId());
        return dto;
    }
}