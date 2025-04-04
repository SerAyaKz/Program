package kz.com.SerAya.dto;

import kz.com.SerAya.entity.LearningOutcome;
import kz.com.SerAya.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LearningOutcomeDto {
    private Integer id;
    private String code;
    private String learningOutcomeKz;
    private String learningOutcomeRu;
    private String learningOutcomeEn;
    private Integer programId;

    public static LearningOutcomeDto fromEntity(LearningOutcome learningOutcome) {
        if (learningOutcome == null) {
            return null;
        }

        return LearningOutcomeDto.builder()
                .id(learningOutcome.getId())
                .code(learningOutcome.getCode())
                .learningOutcomeKz(learningOutcome.getLearningOutcomeKz())
                .learningOutcomeRu(learningOutcome.getLearningOutcomeRu())
                .learningOutcomeEn(learningOutcome.getLearningOutcomeEn())
                .programId(learningOutcome.getProgram() != null ? learningOutcome.getProgram().getId() : null)
                .build();
    }

    public static LearningOutcome toEntity(LearningOutcomeDto dto, Program program) {
        if (dto == null) {
            return null;
        }

        return LearningOutcome.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .learningOutcomeKz(dto.getLearningOutcomeKz())
                .learningOutcomeRu(dto.getLearningOutcomeRu())
                .learningOutcomeEn(dto.getLearningOutcomeEn())
                .program(program)
                .build();
    }
}
