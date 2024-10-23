package kz.com.SerAya.Service.impl;

import kz.com.SerAya.DTO.ProgramDto;
import kz.com.SerAya.DTO.SectionDto;
import kz.com.SerAya.DTO.StandardDto;
import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.Section;
import kz.com.SerAya.Entity.Standard;
import kz.com.SerAya.Repository.ProgramRepository;
import kz.com.SerAya.Repository.SectionRepository;
import kz.com.SerAya.Service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository repository;
    private final SectionRepository sectionRepository;

    @Override
    public Integer save(ProgramDto dto) {
        Program program = ProgramDto.toEntity(dto);

        Program savedProgram = repository.save(program);

        createDefaultSections(savedProgram);

        return savedProgram.getId();
    }

    @Override
    public List<ProgramDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ProgramDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDto findById(Integer id) {
        return repository.findById(id)
                .map(ProgramDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }
    @Override
    public Program findProgramById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }
    @Override
    public List<Program> findAllPrograms() {
        return repository.findAll();
    }
    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, ProgramDto programDto) {

        Program existingProgram = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No standard found with the ID: " + id));

        existingProgram.setCode(programDto.getCode());
        existingProgram.setName(programDto.getName());
        existingProgram.setEducationFieldCode(programDto.getEducationFieldCode());
        existingProgram.setEducationFieldName(programDto.getEducationFieldName());
        existingProgram.setTrainingDirectionCode(programDto.getTrainingDirectionCode());
        existingProgram.setTrainingDirectionName(programDto.getTrainingDirectionName());
        existingProgram.setProgramGroup(programDto.getProgramGroup());
        existingProgram.setIscedLevel(programDto.getIscedLevel());
        existingProgram.setNqfLevel(programDto.getNqfLevel());
        existingProgram.setSqfLevel(programDto.getSqfLevel());
        existingProgram.setStudyDurationYears(programDto.getStudyDurationYears());
        existingProgram.setCredits(programDto.getCredits());
        existingProgram.setModifiedDate(LocalDateTime.now());
        repository.save(existingProgram);
    }

    private void createDefaultSections(Program program) {
        Section parentSection1 = SectionDto.toEntity(
                new SectionDto(null, "Описание образовательной программы", null,null, null, 1, 1, program.getId()), program, sectionRepository
        );

        Section parentSection2 = SectionDto.toEntity(
                new SectionDto(null, "Цель и задачи образовательной программы", null, null,null, 1, 2, program.getId()), program, sectionRepository
        );

        Section parentSection3 = SectionDto.toEntity(
                new SectionDto(null, "Требования к оценке результатов обучения образовательной программы", null,null, null, 1, 3, program.getId()), program, sectionRepository
        );

        Section parentSection4 = SectionDto.toEntity(
                new SectionDto(null, "Паспорт образовательной программы", null,null, null, 1, 4, program.getId()), program, sectionRepository
        );

        Section parentSection5 = SectionDto.toEntity(
                new SectionDto(null, "Перечень дополнительных образовательных программ", null,null, null, 1, 5, program.getId()), program, sectionRepository
        );

        List<Section> parentSections = List.of(parentSection1, parentSection2, parentSection3, parentSection4, parentSection5);
        List<Section> savedParentSections = sectionRepository.saveAll(parentSections);
//        System.out.println(Arrays.toString(savedParentSections.toArray()));
        List<Section> childSections = List.of(
                SectionDto.toEntity(new SectionDto(null, "Общие сведения", null,null, savedParentSections.get(3).getId(), 2, 1, program.getId()), program, sectionRepository),
                SectionDto.toEntity(new SectionDto(null, "Матрица соотнесения результатов обучения образовательной программы с формируемыми компетенциями", null,null, savedParentSections.get(3).getId(), 2, 2, program.getId()), program, sectionRepository),
                SectionDto.toEntity(new SectionDto(null, "Сведения о модулях / дисциплинах", null,null, savedParentSections.get(3).getId(), 2, 3, program.getId()), program, sectionRepository)
        );

        sectionRepository.saveAll(childSections);
    }
}
