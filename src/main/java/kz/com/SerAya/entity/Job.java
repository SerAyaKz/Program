package kz.com.SerAya.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonProperty("job_title")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("job_description")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "job_type")
    private String job_type;

    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id", nullable = false)
    private Program program;

}