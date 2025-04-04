package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Standard;
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
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private Integer programId;

    public static StandardDto fromEntity(Standard standard, Integer programId) {
        if (standard == null) {
            return null;
        }

        return StandardDto.builder()
                .id(standard.getId())
                .nameKz(standard.getNameKz())
                .nameRu(standard.getNameRu())
                .nameEn(standard.getNameEn())
                .programId(programId)
                .build();
    }

    public static Standard toEntity(StandardDto standardDto) {
        if (standardDto == null) {
            return null;
        }

        return Standard.builder()
                .id(standardDto.getId())
                .nameKz(standardDto.getNameKz())
                .nameRu(standardDto.getNameRu())
                .nameEn(standardDto.getNameEn())
                .build();
    }
}
