package kz.com.SerAya.dto;

import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RecommendationDto {
    private Integer id;
    private String content;
    private Integer program_id;

    public static RecommendationDto fromEntity(Recommendation recommendation) {
        return RecommendationDto.builder()
                .id(recommendation.getId())
                .content(recommendation.getContent())
                .program_id(recommendation.getProgram() != null ? recommendation.getProgram().getId() : null)
                .build();
    }

    public static Recommendation toEntity(RecommendationDto recommendationDto, Program program) {
        return Recommendation.builder()
                .id(recommendationDto.getId())
                .content(recommendationDto.getContent())
                .program(program)
                .build();
    }
}
