package kz.com.SerAya.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

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

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "cycle")
    private String cycle;

    @Column(name = "type")
    private String type;

//    @Column(name = "number_of_credits")
//    private int numberOfCredits;
//
//    @Column(name = "trimester")
//    private int trimester;


}