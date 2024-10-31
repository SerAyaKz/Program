package kz.com.SerAya.entity;

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
@Table(name = "job_skill")
public class Skill{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "freq", nullable = false)
    private int freq;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)
    private Job job;
}
