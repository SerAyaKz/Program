package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Section;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.repository.SectionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SectionDto {
    private Integer id;
    private String sectionTitle;
    private String sectionPrompt;
    private String sectionContent;
    private String numbering;
    private List<SectionDto> children = new ArrayList<>();
    private Integer programId; // Store the program's ID for simplicity

    public static SectionDto fromEntity(Section section) {
        if (section == null) {
            return null;
        }

        return SectionDto.builder()
                .id(section.getId())
                .sectionTitle(section.getSectionTitle())
                .sectionPrompt(section.getSectionPrompt())
                .sectionContent(section.getSectionContent())
                .numbering(section.getNumbering())
                .programId(section.getProgram() != null ? section.getProgram().getId() : null)
                .children(section.getChildren() != null
                        ? section.getChildren().stream()
                        .map(SectionDto::fromEntity)
                        .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public static Section toEntity(SectionDto sectionDto, Program program, SectionRepository sectionRepository) {
        if (sectionDto == null) {
            return null;
        }

        Section section = Section.builder()
                .id(sectionDto.getId() != null ? sectionDto.getId() : null)
                .sectionTitle(sectionDto.getSectionTitle())
                .sectionPrompt(sectionDto.getSectionPrompt())
                .sectionContent(sectionDto.getSectionContent())
                .numbering(sectionDto.getNumbering())
                .program(program)
                .build();

        // Retrieve existing children if any and map them back to entities
        if (sectionDto.getChildren() != null && !sectionDto.getChildren().isEmpty()) {
            List<Section> childEntities = sectionDto.getChildren().stream()
                    .map(childDto -> toEntity(childDto, program, sectionRepository))
                    .collect(Collectors.toList());
            section.setChildren(childEntities);
        }

        return section;
    }
}
