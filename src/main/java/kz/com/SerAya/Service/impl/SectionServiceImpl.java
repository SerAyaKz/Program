package kz.com.SerAya.Service.impl;

import kz.com.SerAya.DTO.SectionDto;
import kz.com.SerAya.DTO.SectionDto;
import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.Section;
import kz.com.SerAya.Entity.Section;
import kz.com.SerAya.Repository.ProgramRepository;
import kz.com.SerAya.Repository.SectionRepository;
import kz.com.SerAya.Repository.SectionRepository;
import kz.com.SerAya.Service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;
    private final ProgramRepository programRepository;

    @Override
    public Integer save(SectionDto dto) {
        Program program = programRepository.findById(dto.getProgram_id()).orElseThrow(EntityNotFoundException::new);

        Section section = SectionDto.toEntity(dto,program,repository);

        Section savedSection = repository.save(section);



        return savedSection.getId();
    }

    @Override
    public List<SectionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(SectionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public SectionDto findById(Integer id) {
        return repository.findById(id)
                .map(SectionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No section found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, SectionDto sectionDto) {
        Section existingSection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No section found with the ID: " + id));

        existingSection.setSectionTitle(sectionDto.getSectionTitle());
        existingSection.setSectionContent(sectionDto.getSectionContent());
        existingSection.setSectionPrompt(sectionDto.getSectionPrompt());
        existingSection.setHierarchyLevel(sectionDto.getHierarchyLevel());
        existingSection.setPositionInProgram(sectionDto.getPositionInProgram());

        if (sectionDto.getParentSectionId() != null) {
            Section parentSection = repository.findById(sectionDto.getParentSectionId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent section not found"));
            existingSection.setParentSection(parentSection);
        } else {
            existingSection.setParentSection(null);
        }

        existingSection.setLastUpdated(LocalDateTime.now());

        repository.save(existingSection);
    }


    @Override
    public List<Section> findSectionsByProgram(Integer id) {
        return repository.findSectionsByProgram(id);
    }
}
