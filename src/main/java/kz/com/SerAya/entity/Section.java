package kz.com.SerAya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "section_title")
    private String sectionTitle;

    @Column(name = "section_prompt")
    private String sectionPrompt;

    @Column(name = "section_content")
    private String sectionContent;

    @Column(name = "numbering")
    private String numbering;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id") // Assuming a self-referencing relationship
    private List<Section> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    public void addChild(Section child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        this.children.add(child);
    }
}
