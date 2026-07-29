package kz.com.SerAya.entity; // TODO: adjust to your actual base package

import kz.com.SerAya.enums.JoinRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "join_requests")
// Note: duplicate-PENDING-request prevention is enforced in JoinRequestService,
// not via a DB constraint, since a user may legitimately have multiple
// resolved (APPROVED/REJECTED) requests for the same program over time.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private JoinRequestStatus status = JoinRequestStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime requestedDate;

    private LocalDateTime resolvedDate;
}