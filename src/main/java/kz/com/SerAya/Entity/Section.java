package kz.com.SerAya.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    @Column(name = "section_title")
    private String sectionTitle;

    @Column(name = "section_prompt")
    private String sectionPrompt;

    @Column(name = "section_content")
    private String sectionContent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_section_id", referencedColumnName = "id")
    private Section parentSection;

    @Column(name = "hierarchy_level")
    private int hierarchyLevel;

    @Column(name = "position_in_program")
    private int positionInProgram;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;


}