package kz.com.SerAya.DTO;

import kz.com.SerAya.Entity.Section;
import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.User;
import kz.com.SerAya.Repository.SectionRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SectionDto {
    private Integer id;
    private String sectionTitle;
    private String sectionContent;
    private String sectionPrompt;
    private Integer parentSectionId; // If there is a parent section
    private int hierarchyLevel;
    private int positionInProgram;
    private Integer program_id;

    public static SectionDto fromEntity(Section section) {
        return SectionDto.builder()
                .id(section.getId())
                .sectionTitle(section.getSectionTitle())
                .sectionContent(section.getSectionContent())
                .sectionPrompt(section.getSectionPrompt())
                .parentSectionId(section.getParentSection() != null ? section.getParentSection().getId() : null)
                .hierarchyLevel(section.getHierarchyLevel())
                .positionInProgram(section.getPositionInProgram())
                .program_id(section.getProgram() != null ? section.getProgram().getId() : null)
                .build();
    }

    public static Section toEntity(SectionDto sectionDto, Program program, SectionRepository sectionRepository) {
        Section parentSection = null;
        if (sectionDto.getParentSectionId() != null) {
            parentSection = sectionRepository.findById(sectionDto.getParentSectionId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent section not found"));
        }
        return Section.builder()
                .id(sectionDto.getId())
                .sectionTitle(sectionDto.getSectionTitle())
                .sectionContent(sectionDto.getSectionContent())
                .sectionPrompt(sectionDto.getSectionPrompt())
                .parentSection(parentSection)
                .hierarchyLevel(sectionDto.getHierarchyLevel())
                .positionInProgram(sectionDto.getPositionInProgram())
                .program(program)
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
