package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProgramDto {

    private Integer id;
    private String codeName; // Updated field name
    private String academicDegree; // Added missing field
    private String eduGoalKz;
    private String eduGoalRu;
    private String eduGoalEn;
    private String directionCodeName;
    private int iscedLevel;
    private int nqfLevel;
    private int sqfLevel;
    private int studyDurationYears;
    private int creditsCount; // Updated field name
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static ProgramDto fromEntity(Program program) {
        if (program == null) {
            return null;
        }
        return ProgramDto.builder()
                .id(program.getId())
                .codeName(program.getCodeName())
                .academicDegree(program.getAcademicDegree())
                .eduGoalKz(program.getEduGoalKz())
                .eduGoalRu(program.getEduGoalRu())
                .eduGoalEn(program.getEduGoalEn())
                .directionCodeName(program.getDirectionCodeName())
                .iscedLevel(program.getIscedLevel())
                .nqfLevel(program.getNqfLevel())
                .sqfLevel(program.getSqfLevel())
                .studyDurationYears(program.getStudyDurationYears())
                .creditsCount(program.getCreditsCount()) // Updated field name
                .createdDate(program.getCreatedDate())
                .modifiedDate(program.getModifiedDate())
                .build();
    }

    public static Program toEntity(ProgramDto programDto) {
        if (programDto == null) {
            return null;
        }

        return Program.builder()
//                .id(programDto.getId())
                .codeName(programDto.getCodeName())
                .academicDegree(programDto.getAcademicDegree())
                .eduGoalKz(programDto.getEduGoalKz())
                .eduGoalRu(programDto.getEduGoalRu())
                .eduGoalEn(programDto.getEduGoalEn())
                .directionCodeName(programDto.getDirectionCodeName())
                .iscedLevel(programDto.getIscedLevel())
                .nqfLevel(programDto.getNqfLevel())
                .sqfLevel(programDto.getSqfLevel())
                .studyDurationYears(programDto.getStudyDurationYears())
                .creditsCount(programDto.getCreditsCount())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
