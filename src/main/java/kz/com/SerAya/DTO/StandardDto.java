package kz.com.SerAya.DTO;

import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.Standard;
import kz.com.SerAya.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StandardDto {

    private Integer id;
    private String name;
    private Integer user_id;

    public static StandardDto fromEntity(Standard standard) {
        if (standard == null) {
            return null;
        }

        return StandardDto.builder()
                .id(standard.getId())
                .name(standard.getName())
                .user_id(standard.getUser() != null ? standard.getUser().getId() : null)
                .build();
    }

    public static Standard toEntity(StandardDto standardDto, User user) {
        if (standardDto == null) {
            return null;
        }

        return Standard.builder()
                .id(standardDto.getId())
                .name(standardDto.getName())
                .user(user)
                .build();
    }
}