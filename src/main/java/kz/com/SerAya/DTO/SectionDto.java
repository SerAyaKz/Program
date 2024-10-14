package kz.com.SerAya.DTO;

import kz.com.SerAya.Entity.Section;
import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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

    public static SectionDto fromEntity(Section section) {
        return SectionDto.builder()
                .id(section.getId())
                .sectionTitle(section.getSectionTitle())
                .sectionContent(section.getSectionContent())
                .sectionPrompt(section.getSectionPrompt())
                .parentSectionId(section.getParentSection() != null ? section.getParentSection().getId() : null)
                .hierarchyLevel(section.getHierarchyLevel())
                .positionInProgram(section.getPositionInProgram())
                .build();
    }

    public static Section toEntity(SectionDto sectionDto, Program program) {
        return Section.builder()
                .id(sectionDto.getId())
                .sectionTitle(sectionDto.getSectionTitle())
                .sectionContent(sectionDto.getSectionContent())
                .sectionPrompt(sectionDto.getSectionPrompt())
                .parentSection(
                        Section.builder()
                                .id(sectionDto.parentSectionId)
                                .build()
                )
                .hierarchyLevel(sectionDto.getHierarchyLevel())
                .positionInProgram(sectionDto.getPositionInProgram())
                .program(program)
                .createdDate(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
