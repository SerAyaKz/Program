package kz.com.SerAya.Service.impl;


import kz.com.SerAya.DTO.StandardDto;
import kz.com.SerAya.DTO.StandardDto;
import kz.com.SerAya.Entity.*;
import kz.com.SerAya.Entity.Standard;
import kz.com.SerAya.Repository.ProgramRepository;
import kz.com.SerAya.Repository.StandardRepository;
import kz.com.SerAya.Repository.UserRepository;
import kz.com.SerAya.Service.StandardService;
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
