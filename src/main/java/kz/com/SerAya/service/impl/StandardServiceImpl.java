package kz.com.SerAya.service.impl;


import kz.com.SerAya.dto.StandardDto;
import kz.com.SerAya.dto.StandardDto;
import kz.com.SerAya.entity.*;
import kz.com.SerAya.entity.Standard;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.repository.ProgramStandardRepository;
import kz.com.SerAya.repository.StandardRepository;
import kz.com.SerAya.service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandardServiceImpl implements StandardService {
    private final ProgramRepository programRepository;
    private final ProgramStandardRepository programStandardRepository;

    private final StandardRepository repository;

    @Override
    public Integer save(StandardDto dto) {

        Standard standard = StandardDto.toEntity(dto);
        Standard savedStandard = repository.save(standard);
        
        programStandardRepository.addStandardToProgram(dto.getProgramId(), savedStandard.getId());
        
        return savedStandard.getId();
    }

    @Override
    public List<StandardDto> findAll() {
        return repository.findAll()
                .stream()
                .map(standard -> StandardDto.fromEntity(standard, 0))
                .collect(Collectors.toList());
    }

    @Override
    public StandardDto findById(Integer id) {
        return repository.findById(id)
                .map(standard -> StandardDto.fromEntity(standard, 0))
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        programStandardRepository.removeStandardFromProgram(id);
        repository.deleteById(id);
    }

    @Override
    public List<StandardDto> findStandardsByProgram(Integer id) {
        return repository.findStandardsByProgram(id).stream()
                .map(standard -> StandardDto.fromEntity(standard, id))
                .collect(Collectors.toList());
    }
    @Override
    public void update(Integer id, StandardDto standardDto) {

        Standard existingStandard = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No standard found with the ID: " + id));

        existingStandard.setNameKz(standardDto.getNameKz());
        existingStandard.setNameRu(standardDto.getNameRu());
        existingStandard.setNameEn(standardDto.getNameEn());

        repository.save(existingStandard);
    }
}
