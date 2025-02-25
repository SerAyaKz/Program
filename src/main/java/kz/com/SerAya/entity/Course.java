package kz.com.SerAya.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "nameKz")
    private String nameKz;

    @Column(name = "nameRu")
    private String nameRu;

    @Column(name = "nameEn")
    private String nameEn;

    @Column(name = "briefInfoKz", length = 500)
    private String briefInfoKz;

    @Column(name = "briefInfoRu", length = 500)
    private String briefInfoRu;

    @Column(name = "briefInfoEn", length = 500)
    private String briefInfoEn;

    @Column(name = "isSelective")
    private boolean isSelective;

    @Column(name = "prerequisites")
    private String prerequisites;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users;

}