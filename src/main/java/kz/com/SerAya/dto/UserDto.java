package kz.com.SerAya.dto;

import kz.com.SerAya.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer id;

    @NotNull(message = "Le prenom ne doit pas etre vide")
    @NotEmpty(message = "Le prenom ne doit pas etre vide")
    @NotBlank(message = "Le prenom ne doit pas etre vide")
    private String displayName;

    @NotNull(message = "L'email ne doit pas etre vide")
    @NotEmpty(message = "L'email ne doit pas etre vide")
    @NotBlank(message = "L'email ne doit pas etre vide")
    @Email(message = "L'email n'est conforme")
    private String email;

    @NotNull(message = "Le mot de passe ne doit pas etre vide")
    @NotEmpty(message = "Le mot de passe ne doit pas etre vide")
    @NotBlank(message = "Le mot de passe ne doit pas etre vide")
    private String photoUrl;
    private String title;
    private Integer roleId;
    private String uid;

    public static UserDto fromEntity(User user) {
        // null check
        return UserDto.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .title(user.getTitle())
                .roleId(user.getRoleId())
                .uid(user.getUid())
                .build();
    }

    public static User toEntity(UserDto user) {
        // null check
        return User.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .title(user.getTitle())
                .roleId(user.getRoleId())
                .uid(user.getUid())
                .build();
    }

}
