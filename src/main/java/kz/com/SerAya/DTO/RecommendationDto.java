package kz.com.SerAya.DTO;

import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.Recommendation;
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
    private String recommendation;
    private Integer program_id;

    public static RecommendationDto fromEntity(Recommendation recommendation) {
        return RecommendationDto.builder()
                .id(recommendation.getId())
                .recommendation(recommendation.getRecommendation())
                .program_id(recommendation.getProgram() != null ? recommendation.getProgram().getId() : null)
                .build();
    }

    public static Recommendation toEntity(RecommendationDto recommendationDto, Program program) {
        return Recommendation.builder()
                .id(recommendationDto.getId())
                .recommendation(recommendationDto.getRecommendation())
                .program(program)
                .build();
    }
}
