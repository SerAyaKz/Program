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
    private Integer program_id;

    public static StandardDto fromEntity(Standard standard) {
        if (standard == null) {
            return null;
        }

        return StandardDto.builder()
                .id(standard.getId())
                .name(standard.getName())
                .program_id(standard.getProgram() != null ? standard.getProgram().getId() : null)
                .build();
    }

    public static Standard toEntity(StandardDto standardDto, Program program) {
        if (standardDto == null) {
            return null;
        }

        return Standard.builder()
                .id(standardDto.getId())
                .name(standardDto.getName())
                .program(program)
                .build();
    }
}