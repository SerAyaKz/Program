package kz.com.SerAya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uid; // Firebase UID

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String photoUrl;

    @Column
    private String fullNameEn;

    @Column
    private String fullNameKz;

    @Column
    private String fullNameRu;

    @Column
    private String jobTitleEn;

    @Column
    private String jobTitleKz;

    @Column
    private String jobTitleRu;

    @Column
    private Integer roleId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_login", nullable = false)
    private LocalDateTime lastLogin = LocalDateTime.now();


}