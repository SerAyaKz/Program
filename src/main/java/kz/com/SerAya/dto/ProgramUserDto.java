package kz.com.SerAya.dto; // TODO: adjust to your actual base package

import kz.com.SerAya.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramUserDto  {

    private Integer id;
    private String displayName;
    private String email;
    private String photoUrl;
    private String fullNameEn;

    private String fullNameKz;

    private String fullNameRu;
    private String jobTitleEn;
    private String jobTitleKz;
    private String jobTitleRu;

    // Programs the user is currently an approved member of
    private List<ProgramDto> programs;

    public static ProgramUserDto fromEntity(User user, List<ProgramDto> programs) {
        return ProgramUserDto.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .fullNameEn(user.getFullNameEn())
                .fullNameKz(user.getFullNameKz())
                .fullNameRu(user.getFullNameRu())
                .jobTitleEn(user.getJobTitleEn())
                .jobTitleKz(user.getJobTitleKz())
                .jobTitleRu(user.getJobTitleRu())
                .programs(programs)
                .build();
    }
}