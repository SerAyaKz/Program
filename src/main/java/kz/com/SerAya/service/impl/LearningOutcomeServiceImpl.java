package kz.com.SerAya.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.com.SerAya.dto.LearningOutcomeDto;
import kz.com.SerAya.entity.CourseProgram;
import kz.com.SerAya.entity.LearningOutcome;
import kz.com.SerAya.entity.Program;

import kz.com.SerAya.repository.CourseProgramRepository;
import kz.com.SerAya.repository.LearningOutcomeRepository;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.service.LearningOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearningOutcomeServiceImpl implements LearningOutcomeService {
    private final ProgramRepository programRepository;
    private final LearningOutcomeRepository repository;
    private final RestTemplate restTemplate;
    private final CourseProgramRepository courseProgramRepository;

    @Override
    public Integer save(LearningOutcomeDto dto) {
        Program program = programRepository.findById(dto.getProgramId()).orElseThrow(EntityNotFoundException::new);
        LearningOutcome learningOutcome = LearningOutcomeDto.toEntity(dto, program);
        LearningOutcome savedLearningOutcome = repository.save(learningOutcome);

        return savedLearningOutcome.getId();
    }

    @Override
    public List<LearningOutcomeDto> findAll() {
        return repository.findAll()
                .stream()
                .map(LearningOutcomeDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public LearningOutcomeDto findById(Integer id) {
        return repository.findById(id)
                .map(LearningOutcomeDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);

    }

    @Override
    public List<LearningOutcomeDto> findLearningOutcomesByProgram(Integer id) {
        return repository.findLearningOutcomesByProgram(id).stream()
                .map(LearningOutcomeDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void update(Integer id, LearningOutcomeDto learningOutcomeDto) {

        LearningOutcome existingLearningOutcome = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No learningOutcome found with the ID: " + id));

        existingLearningOutcome.setCode(learningOutcomeDto.getCode());
        existingLearningOutcome.setLearningOutcomeKz(learningOutcomeDto.getLearningOutcomeKz());
        existingLearningOutcome.setLearningOutcomeRu(learningOutcomeDto.getLearningOutcomeRu());
        existingLearningOutcome.setLearningOutcomeEn(learningOutcomeDto.getLearningOutcomeEn());

        if (learningOutcomeDto.getProgramId() != null) {
            Program program = programRepository.findById(learningOutcomeDto.getProgramId())
                    .orElseThrow(() -> new EntityNotFoundException("No Program found with the ID: " + learningOutcomeDto.getProgramId()));
            existingLearningOutcome.setProgram(program);
        }
        repository.save(existingLearningOutcome);
    }

    public void generate(Integer programId) {
        String flaskUrl = "http://localhost:5000/generate_learningOutcome_titles";
        Program program = programRepository.findById(programId).orElseThrow(EntityNotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a description JSON object
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", program.getCodeName());

        // Convert to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return; // Handle JSON processing error
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);

        // Capture the response as String
        ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, requestEntity, String.class);

        System.out.println(response.getBody());

        try {
            List<LearningOutcome> learningOutcomes = objectMapper.readValue(response.getBody(), new TypeReference<List<LearningOutcome>>() {});

            // Save each learningOutcome in the database
            for (LearningOutcome learningOutcome : learningOutcomes) {
//                learningOutcome.setProgram(program);
//                repository.save(learningOutcome); // Save the learningOutcome entity
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error (e.g., log the error or rethrow it)
        }
    }


}
