package kz.com.SerAya.DTO;

import kz.com.SerAya.Entity.Program;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.User;
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
    private String code;
    private String name;
    private String educationFieldCode;
    private String educationFieldName;
    private String trainingDirectionCode;
    private String trainingDirectionName;
    private String programGroup;
    private int iscedLevel;
    private int nqfLevel;
    private int sqfLevel;
    private int studyDurationYears;
    private int credits;
    private Integer createdById; // ID of the user who created the program

    // Converts Program entity to ProgramDto
    public static ProgramDto fromEntity(Program program) {
        if (program == null) {
            return null;
        }
        return ProgramDto.builder()
                .id(program.getId())
                .code(program.getCode())
                .name(program.getName())
                .educationFieldCode(program.getEducationFieldCode())
                .educationFieldName(program.getEducationFieldName())
                .trainingDirectionCode(program.getTrainingDirectionCode())
                .trainingDirectionName(program.getTrainingDirectionName())
                .programGroup(program.getProgramGroup())
                .iscedLevel(program.getIscedLevel())
                .nqfLevel(program.getNqfLevel())
                .sqfLevel(program.getSqfLevel())
                .studyDurationYears(program.getStudyDurationYears())
                .credits(program.getCredits())
                .createdById(program.getCreatedBy() != null ? program.getCreatedBy().getId() : null)
                .build();
    }

    // Converts ProgramDto to Program entity
    public static Program toEntity(ProgramDto programDto) {
        return Program.builder()
                .id(programDto.id)
                .code(programDto.code)
                .name(programDto.name)
                .educationFieldCode(programDto.educationFieldCode)
                .educationFieldName(programDto.educationFieldName)
                .trainingDirectionCode(programDto.trainingDirectionCode)
                .trainingDirectionName(programDto.trainingDirectionName)
                .programGroup(programDto.programGroup)
                .iscedLevel(programDto.iscedLevel)
                .nqfLevel(programDto.nqfLevel)
                .sqfLevel(programDto.sqfLevel)
                .studyDurationYears(programDto.studyDurationYears)
                .credits(programDto.credits)
                .createdBy(
                        User.builder()
                                .id(programDto.createdById)
                                .build()
                )
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
