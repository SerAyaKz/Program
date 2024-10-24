package kz.com.SerAya.service.impl;

import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.dto.SectionDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Section;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.repository.SectionRepository;
import kz.com.SerAya.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Transactional
    public void createDefaultSections(Program program) {
        List<Section> sections = new ArrayList<>();

        // 1. Описание образовательной программы
        Section section1 = Section.builder()
                .sectionTitle("Описание образовательной программы")
                .sectionPrompt("Описание образовательной программы")
                .sectionContent("Содержимое для раздела 'Описание образовательной программы'")
                .numbering("1")
                .program(program)
                .build();
        sections.add(section1);

        // 2. Цель и задачи образовательной программы
        Section section2 = Section.builder()
                .sectionTitle("Цель и задачи образовательной программы")
                .sectionPrompt("Цель и задачи образовательной программы")
                .sectionContent("Содержимое для раздела 'Цель и задачи образовательной программы'")
                .numbering("2")
                .program(program)
                .build();
        sections.add(section2);

        // 3. Требования к оценке результатов обучения образовательной программы
        Section section3 = Section.builder()
                .sectionTitle("Требования к оценке результатов обучения образовательной программы")
                .sectionPrompt("Требования к оценке")
                .sectionContent("Содержимое для раздела 'Требования к оценке результатов обучения'")
                .numbering("3")
                .program(program)
                .build();
        sections.add(section3);

        // 4. Паспорт образовательной программы
        Section section4 = Section.builder()
                .sectionTitle("Паспорт образовательной программы")
                .sectionPrompt("Паспорт образовательной программы")
                .sectionContent("Содержимое для раздела 'Паспорт образовательной программы'")
                .numbering("4")
                .program(program)
                .build();

        // 4.1 Общие сведения
        Section section4_1 = Section.builder()
                .sectionTitle("Общие сведения")
                .sectionPrompt("Общие сведения")
                .sectionContent("Содержимое для раздела 'Общие сведения'")
                .numbering("4.1")
                .program(program)
                .build();

        // 4.2 Матрица соотнесения результатов обучения образовательной программы с формируемыми компетенциями
        Section section4_2 = Section.builder()
                .sectionTitle("Матрица соотнесения результатов обучения образовательной программы с формируемыми компетенциями")
                .sectionPrompt("Матрица компетенций")
                .sectionContent("Содержимое для раздела 'Матрица соотнесения'")
                .numbering("4.2")
                .program(program)
                .build();

        // 4.3 Сведения о модулях / дисциплинах
        Section section4_3 = Section.builder()
                .sectionTitle("Сведения о модулях / дисциплинах")
                .sectionPrompt("Сведения о модулях")
                .sectionContent("Содержимое для раздела 'Сведения о модулях / дисциплинах'")
                .numbering("4.3")
                .program(program)
                .build();

        // Add the sub-sections to section 4
        section4.addChild(section4_1);
        section4.addChild(section4_2);
        section4.addChild(section4_3);
        sections.add(section4);

        // 5. Перечень дополнительных образовательных программ
        Section section5 = Section.builder()
                .sectionTitle("Перечень дополнительных образовательных программ")
                .sectionPrompt("Перечень программ")
                .sectionContent("Содержимое для раздела 'Перечень дополнительных образовательных программ'")
                .numbering("5")
                .program(program)
                .build();
        sections.add(section5);

        // Save all sections to the database
        sectionRepository.saveAll(sections);
    }
}
