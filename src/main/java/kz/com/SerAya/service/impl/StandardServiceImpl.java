package kz.com.SerAya.service.impl;


import kz.com.SerAya.dto.StandardDto;
import kz.com.SerAya.entity.*;
import kz.com.SerAya.entity.Standard;
import kz.com.SerAya.repository.ProgramRepository;
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
    private final StandardRepository repository;

    @Override
    public Integer save(StandardDto dto) {
        Program program = programRepository.findById(dto.getProgram_id()).orElseThrow(EntityNotFoundException::new);
        Standard standard = StandardDto.toEntity(dto,program);

        Standard savedStandard = repository.save(standard);


        return savedStandard.getId();
    }

    @Override
    public List<StandardDto> findAll() {
        return repository.findAll()
                .stream()
                .map(StandardDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StandardDto findById(Integer id) {
        return repository.findById(id)
                .map(StandardDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

    @Override
    public List<StandardDto> findStandardsByProgram(Integer id) {
        return repository.findStandardsByProgram(id).stream()
                .map(StandardDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void update(Integer id, StandardDto standardDto) {

        Standard existingStandard = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No standard found with the ID: " + id));

        existingStandard.setName(standardDto.getName());

        repository.save(existingStandard);
    }
}
