package kz.com.SerAya.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "standard")
public class Standard{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "nameKz")
    private String nameKz;

    @Column(name = "nameRu")
    private String nameRu;

    @JsonProperty("name_en")
    @Column(name = "nameEn")
    private String nameEn;

}