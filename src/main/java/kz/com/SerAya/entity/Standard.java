package kz.com.SerAya.entity;

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

    @Column(name = "nameKz", nullable = false)
    private String nameKz;

    @Column(name = "nameRu", nullable = false)
    private String nameRu;

    @Column(name = "nameEn", nullable = false)
    private String nameEn;

}