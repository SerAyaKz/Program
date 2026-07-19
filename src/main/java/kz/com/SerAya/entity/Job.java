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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("job_title")
    @Column(name = "nameEn")
    private String nameEn;

    @JsonProperty("job_description")
    @Column(name = "descriptionEn")
    private String descriptionEn;

    @JsonProperty("job_title")
    @Column(name = "nameRu")
    private String nameRu;

    @JsonProperty("job_description")
    @Column(name = "descriptionRu")
    private String descriptionRu;

    @JsonProperty("job_title")
    @Column(name = "nameKz")
    private String nameKz;

    @JsonProperty("job_description")
    @Column(name = "descriptionKz")
    private String descriptionKz;

    @Column(name = "job_type")
    private String job_type;

}