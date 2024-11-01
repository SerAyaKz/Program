package kz.com.SerAya.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.com.SerAya.dto.JobDto;
import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Job;
import kz.com.SerAya.repository.ProgramRepository;
import kz.com.SerAya.repository.JobRepository;
import kz.com.SerAya.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final ProgramRepository programRepository;
    private final JobRepository repository;
    private final RestTemplate restTemplate;

    @Override
    public Integer save(JobDto dto) {
        Program program = programRepository.findById(dto.getProgram_id()).orElseThrow(EntityNotFoundException::new);
        Job job = JobDto.toEntity(dto,program);

        Job savedJob = repository.save(job);


        return savedJob.getId();
    }

    @Override
    public List<JobDto> findAll() {
        return repository.findAll()
                .stream()
                .map(JobDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public JobDto findById(Integer id) {
        return repository.findById(id)
                .map(JobDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

    @Override
    public List<JobDto> findJobsByProgram(Integer id) {
        return repository.findJobsByProgram(id).stream()
                .map(JobDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void update(Integer id, JobDto jobDto) {

        Job existingJob = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No job found with the ID: " + id));

        existingJob.setName(jobDto.getName());
        existingJob.setDescription(jobDto.getDescription());
        repository.save(existingJob);
    }

    public void generate(Integer programId) {
        String flaskUrl = "http://localhost:5000/generate_job_titles";
        Program program = programRepository.findById(programId).orElseThrow(EntityNotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a description JSON object
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", program.getName());

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
            List<Job> jobs = objectMapper.readValue(response.getBody(), new TypeReference<List<Job>>() {});

            // Save each job in the database
            for (Job job : jobs) {
                job.setProgram(program);
                repository.save(job); // Save the job entity
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error (e.g., log the error or rethrow it)
        }
    }
    public void collectSkills(Integer jobId) {
        String flaskUrl = "http://localhost:5000/get_hh_enbek_skills";
        Job job = repository.findById(jobId).orElseThrow(EntityNotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a description JSON object
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("job_name", job.getName());

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
        job.setJob_skill(response.getBody());
        repository.save(job);
    }

}
