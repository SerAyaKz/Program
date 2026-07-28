package kz.com.SerAya.entity; // TODO: adjust to your actual base package

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents actual membership: a user who has been approved into a program.
 * Rows here are created when a JoinRequest is approved.
 */
@Entity
@Table(name = "user_programs", uniqueConstraints = {
        @UniqueConstraint(name = "uq_user_program", columnNames = {"user_id", "program_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(nullable = false)
    private LocalDateTime joinedDate;
}