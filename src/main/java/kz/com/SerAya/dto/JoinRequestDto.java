package kz.com.SerAya.dto; // TODO: adjust to your actual base package

import kz.com.SerAya.enums.JoinRequestStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequestDto {

    private Integer id;

    @NotNull(message = "userId ne doit pas etre vide")
    private Integer userId;

    private String userDisplayName;

    @NotNull(message = "programId ne doit pas etre vide")
    private Integer programId;

    private String programCodeName;

    private JoinRequestStatus status;

    private LocalDateTime requestedDate;

    private LocalDateTime resolvedDate;

    public static JoinRequestDto fromEntity(JoinRequest joinRequest) {
        return JoinRequestDto.builder()
                .id(joinRequest.getId())
                .userId(joinRequest.getUser().getId())
                .userDisplayName(joinRequest.getUser().getDisplayName())
                .programId(joinRequest.getProgram().getId())
                .programCodeName(joinRequest.getProgram().getCodeName())
                .status(joinRequest.getStatus())
                .requestedDate(joinRequest.getRequestedDate())
                .resolvedDate(joinRequest.getResolvedDate())
                .build();
    }
}