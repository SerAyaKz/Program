package kz.com.SerAya.Service.impl;


import kz.com.SerAya.DTO.RecommendationDto;
import kz.com.SerAya.Entity.Program;
import kz.com.SerAya.Entity.Recommendation;
import kz.com.SerAya.Repository.ProgramRepository;
import kz.com.SerAya.Repository.RecommendationRepository;
import kz.com.SerAya.Service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final ProgramRepository programRepository;
    private final RecommendationRepository repository;

    @Override
    public Integer save(RecommendationDto dto) {
        Program program = programRepository.findById(dto.getProgram_id()).orElseThrow(EntityNotFoundException::new);
        Recommendation recommendation = RecommendationDto.toEntity(dto,program);

        Recommendation savedRecommendation = repository.save(recommendation);


        return savedRecommendation.getId();
    }

    @Override
    public List<RecommendationDto> findAll() {
        return repository.findAll()
                .stream()
                .map(RecommendationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RecommendationDto findById(Integer id) {
        return repository.findById(id)
                .map(RecommendationDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

   
}
