package kz.com.SerAya.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.com.SerAya.dto.LearningOutcomeDto;
import kz.com.SerAya.dto.ProgramDataDto;
import kz.com.SerAya.dto.ProgramDto;
import kz.com.SerAya.dto.SectionDto;
import kz.com.SerAya.entity.*;
import kz.com.SerAya.repository.*;
import kz.com.SerAya.service.ProgramService;
import kz.com.SerAya.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository repository;
    private final SectionRepository sectionRepository;
    private final LearningOutcomeRepository learningOutcomeRepository;
    private final CourseProgramRepository courseProgramRepository;
    private final JobRepository jobRepository;
    private final StandardRepository standardRepository;
    private final RecommendationRepository recommendationRepository;
    private final ProgramStandardRepository programStandardRepository;
    private final CourseRepository courseRepository;
    private final ProgramJobRepository programJobRepository;
    private final CourseUserRepository courseUserRepository;

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Override
    public Integer save(ProgramDto dto) {
        Program program = ProgramDto.toEntity(dto);

        Program savedProgram = repository.save(program);

        createDefaultSections(savedProgram);

        return savedProgram.getId();
    }

    @Override
    public List<ProgramDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ProgramDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDto findById(Integer id) {
        return repository.findById(id)
                .map(ProgramDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }
    @Override
    public Program findProgramById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID : " + id));
    }
    @Override
    public List<Program> findAllPrograms() {
        return repository.findAll();
    }
    @Override
    public void delete(Integer id) {
        // todo check delete
        learningOutcomeRepository.deleteByProgram(id);
        sectionRepository.deleteAllByProgram(id);
        courseProgramRepository.deleteByProgram(id);
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, ProgramDto programDto) {

        Program existingProgram = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No program found with the ID: " + id));

        existingProgram.setCodeName(programDto.getCodeName()); // Updated field name
        existingProgram.setAcademicDegree(programDto.getAcademicDegree()); // Assuming it exists in DTO

        existingProgram.setEduGoalKz(programDto.getEduGoalKz());
        existingProgram.setEduGoalRu(programDto.getEduGoalRu());
        existingProgram.setEduGoalEn(programDto.getEduGoalEn());

        existingProgram.setDirectionCodeName(programDto.getDirectionCodeName()); // Matches `trainingDirectionCode`
        existingProgram.setIscedLevel(programDto.getIscedLevel());
        existingProgram.setNqfLevel(programDto.getNqfLevel());
        existingProgram.setSqfLevel(programDto.getSqfLevel());
        existingProgram.setStudyDurationYears(programDto.getStudyDurationYears());
        existingProgram.setCreditsCount(programDto.getCreditsCount()); // Updated `credits` → `creditsCount`

        existingProgram.setModifiedDate(LocalDateTime.now());

        repository.save(existingProgram);
    }

    @Transactional
    public void createDefaultSections(Program program) {
        List<Section> sections = new ArrayList<>();

        // 1. Описание образовательной программы
        Section section1 = Section.builder()
                .sectionTitle("Описание образовательной программы")
                .sectionPrompt("Описание образовательной программы")
                .sectionContent("Содержимое для раздела 'Описание образовательной программы'")
                .numbering("1")
                .program(program)
                .build();
        sections.add(section1);

        // 2. Цель и задачи образовательной программы
        Section section2 = Section.builder()
                .sectionTitle("Цель и задачи образовательной программы")
                .sectionPrompt("Цель и задачи образовательной программы")
                .sectionContent("Содержимое для раздела 'Цель и задачи образовательной программы'")
                .numbering("2")
                .program(program)
                .build();
        sections.add(section2);

        // 3. Требования к оценке результатов обучения образовательной программы
        Section section3 = Section.builder()
                .sectionTitle("Требования к оценке результатов обучения образовательной программы")
                .sectionPrompt("Требования к оценке")
                .sectionContent("Содержимое для раздела 'Требования к оценке результатов обучения'")
                .numbering("3")
                .program(program)
                .build();
        sections.add(section3);

        // 4. Паспорт образовательной программы
        Section section4 = Section.builder()
                .sectionTitle("Паспорт образовательной программы")
                .sectionPrompt("Паспорт образовательной программы")
                .sectionContent("Содержимое для раздела 'Паспорт образовательной программы'")
                .numbering("4")
                .program(program)
                .build();

        // 4.1 Общие сведения
        Section section4_1 = Section.builder()
                .sectionTitle("Общие сведения")
                .sectionPrompt("Общие сведения")
                .sectionContent("Содержимое для раздела 'Общие сведения'")
                .numbering("4.1")
                .program(program)
                .build();

        // 4.2 Матрица соотнесения результатов обучения образовательной программы с формируемыми компетенциями
        Section section4_2 = Section.builder()
                .sectionTitle("Матрица соотнесения результатов обучения образовательной программы с формируемыми компетенциями")
                .sectionPrompt("Матрица компетенций")
                .sectionContent("Содержимое для раздела 'Матрица соотнесения'")
                .numbering("4.2")
                .program(program)
                .build();

        // 4.3 Сведения о модулях / дисциплинах
        Section section4_3 = Section.builder()
                .sectionTitle("Сведения о модулях / дисциплинах")
                .sectionPrompt("Сведения о модулях")
                .sectionContent("Содержимое для раздела 'Сведения о модулях / дисциплинах'")
                .numbering("4.3")
                .program(program)
                .build();

        // Add the sub-sections to section 4
        section4.addChild(section4_1);
        section4.addChild(section4_2);
        section4.addChild(section4_3);
        sections.add(section4);

        // 5. Перечень дополнительных образовательных программ
        Section section5 = Section.builder()
                .sectionTitle("Перечень дополнительных образовательных программ")
                .sectionPrompt("Перечень программ")
                .sectionContent("Содержимое для раздела 'Перечень дополнительных образовательных программ'")
                .numbering("5")
                .program(program)
                .build();
        sections.add(section5);

        // Save all sections to the database
        sectionRepository.saveAll(sections);
    }

    public Map<String, Object> getDashboardData() {
        Map<String, Object> response = new HashMap<>();

        // Add all dashboard data to response
        response.put("programsByAcademicDegree", getProgramsByAcademicDegree());
        response.put("programsByEducationalLevels", getProgramsByEducationalLevels());
        response.put("coursesBySelectiveStatus", getCoursesBySelectiveStatus());
        response.put("courseDistributionByTerm", getCourseDistributionByTerm());
        response.put("creditDistribution", getCreditDistribution());
        response.put("userActivityTimeline", getUserActivityTimeline());
        response.put("programParticipation", getProgramParticipation());
        response.put("courseAssignmentDistribution", getCourseAssignmentDistribution());
        response.put("learningOutcomesPerProgram", getLearningOutcomesPerProgram());
        response.put("jobsByProgram", getJobsByProgram());
        response.put("jobTypeDistribution", getJobTypeDistribution());
        response.put("programHealthMetrics", getProgramHealthMetrics());
        response.put("coursePrerequisitesNetwork", getCoursePrerequisitesNetwork());
        response.put("recentProgramActivity", getRecentProgramActivity());
        response.put("programCompleteness", getProgramCompleteness());
        response.put("courseLanguageDistribution", getCourseLanguageDistribution());
        response.put("topStandardsUsed", getTopStandardsUsed());
        response.put("mostAssignedCourses", getMostAssignedCourses());
        response.put("programCreditsSummary", getProgramCreditsSummary());
        response.put("userRolesDistribution", getUserRolesDistribution());
        response.put("programsByDirection", getProgramsByDirection());
        response.put("coursesProgramsTimeline", getCoursesProgramsTimeline());
        response.put("userCreationTimeline", getUserCreationTimeline());
        response.put("learningOutcomesLanguageCompleteness", getLearningOutcomesLanguageCompleteness());
        response.put("courseDurationSummary", getCourseDurationSummary());

        return response;
    }

    @Override
    public ProgramDataDto findProgramDataById(Integer id) {
        Program program = repository.findById(id).orElseThrow();

        List<Job> jobs = jobRepository.findJobsByProgram(id);
        List<Standard> standards = standardRepository.findStandardsByProgram(id);
        List<LearningOutcome> outcomes = learningOutcomeRepository.findLearningOutcomesByProgram(id);
        List<CourseProgram> courses = courseProgramRepository.findCourseProgramsByProgram(id);

        return ProgramDataDto.fromEntity(
                program,
                jobs,
                standards,
                outcomes,
                courses
        );
    }

    @Override
    public void generateRecommendation(Integer id) {

    }

//    public void generateGoal(Integer programId) {
//        String flaskUrl = "http://127.0.0.1:5000/goal";
//        Program program = repository.findById(programId).orElseThrow(EntityNotFoundException::new);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Create a description JSON object
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("program", program.getCodeName());
//
//        // Convert to JSON string
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonRequestBody;
//        try {
//            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return; // Handle JSON processing error
//        }
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);
//
//        // Capture the response as String
//        ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, requestEntity, String.class);
//        program.setEduGoalEn(response.getBody());
//        System.out.println(response.getBody());
//
//
//    }
//public void generateGoal(Integer programId) {
//    Program program = repository.findById(programId).orElseThrow(EntityNotFoundException::new);
//
//    String baseUrl = "https://fralet-flask4platform.hf.space/gradio_api/call/generate_goals";
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//
//    // --- Step 1: POST to start the job ---
//    Map<String, Object> requestBody = new HashMap<>();
//    requestBody.put("data", List.of(program.getCodeName()));
//
//    String jsonRequestBody;
//    try {
//        jsonRequestBody = objectMapper.writeValueAsString(requestBody);
//    } catch (JsonProcessingException e) {
//        e.printStackTrace();
//        return;
//    }
//
//    HttpEntity<String> postEntity = new HttpEntity<>(jsonRequestBody, headers);
//    ResponseEntity<String> postResponse;
//    try {
//        postResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, postEntity, String.class);
//    } catch (RestClientException e) {
//        e.printStackTrace();
//        return;
//    }
//
//    String eventId;
//    try {
//        JsonNode postJson = objectMapper.readTree(postResponse.getBody());
//        eventId = postJson.get("event_id").asText();
//    } catch (Exception e) {
//        e.printStackTrace();
//        return;
//    }
//    System.out.println(eventId);
//
//    // --- Step 2: GET the result using the event_id ---
//    String getUrl = baseUrl + "/" + eventId;
//    String rawResult;
//    try {
//        ResponseEntity<String> getResponse = restTemplate.exchange(getUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
//        rawResult = getResponse.getBody();
//    } catch (RestClientException e) {
//        e.printStackTrace();
//        return;
//    }
//    System.out.println(rawResult);
//
//    if (rawResult == null || rawResult.isBlank()) {
//        System.out.println("Empty response from generate_goals");
//        return;
//    }
//
//    // Gradio's GET endpoint streams SSE-style lines like:
//    // event: complete
//    // data: ["..."]
//    // Extract the last "data:" line's JSON payload.
//    String jsonData = null;
//    for (String line : rawResult.split("\n")) {
//        if (line.startsWith("data:")) {
//            jsonData = line.substring("data:".length()).trim();
//        }
//    }
//    System.out.println("jsonData" + jsonData);
//    if (jsonData == null) {
//        // Fallback: maybe the body was already plain JSON (no SSE wrapper)
//        jsonData = rawResult.trim();
//    }
//
//    String eduGoal;
//    try {
//        JsonNode dataArray = objectMapper.readTree(jsonData);
//        eduGoal = dataArray.get(0).asText();
//    } catch (Exception e) {
//        e.printStackTrace();
//        return;
//    }
//
//    program.setEduGoalEn(eduGoal);
//    repository.save(program); // if you need to persist the change
//
////        System.out.println(eduGoal);
//}

    public void generateGoal(Integer programId) {
        Program program = repository.findById(programId).orElseThrow(EntityNotFoundException::new);

        String baseUrl = "https://showpiece-edging-landscape.ngrok-free.dev/goal";

        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String input = program.getCodeName();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("program", List.of(input));
        requestBody.put("token", 500);
        requestBody.put("lang", "en");

        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpEntity<String> postEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<String> postResponse;
        try {
            postResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, postEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(postResponse.getBody());

        program.setEduGoalEn(postResponse.getBody());
        repository.save(program);
    }

    @Override
    public void generateStandard(Integer programId) {
        Program program = repository.findById(programId).orElseThrow(EntityNotFoundException::new);

        String baseUrl = "https://showpiece-edging-landscape.ngrok-free.dev/standards";

        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String input = program.getCodeName();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("program", List.of(input));
        requestBody.put("token", 500);
        requestBody.put("lang", "en");

        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpEntity<String> postEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<List<Standard>> postResponse;
        try {
            postResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, postEntity, new ParameterizedTypeReference<List<Standard>>() {});
        } catch (RestClientException e) {
            e.printStackTrace();
            return;
        }
        List<Standard> standards = postResponse.getBody();

        if (standards == null || standards.isEmpty()) {
            return;
        }
        for (Standard apiStandard : standards) {

            Standard standard = new Standard();

            standard.setNameEn(apiStandard.getNameEn());


            Standard savedStandard = standardRepository.save(standard);

            programStandardRepository.addStandardToProgram(programId, savedStandard.getId());


        }
    }
    @Override
    public void generateJob(Integer programId) {
        Program program = repository.findById(programId).orElseThrow(EntityNotFoundException::new);

        String baseUrl = "https://showpiece-edging-landscape.ngrok-free.dev/jobs";

        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String input = program.getCodeName();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("program", List.of(input));
        requestBody.put("token", 500);
        requestBody.put("lang", "en");

        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpEntity<String> postEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<List<Job>> postResponse;
        try {
            postResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, postEntity, new ParameterizedTypeReference<List<Job>>() {});
        } catch (RestClientException e) {
            e.printStackTrace();
            return;
        }
        List<Job> jobs = postResponse.getBody();

        if (jobs == null || jobs.isEmpty()) {
            return;
        }
        for (Job apiJob : jobs) {

            Job job = new Job();

            job.setNameEn(apiJob.getNameEn());
            job.setDescriptionEn(apiJob.getDescriptionEn());
            job.setJob_type(apiJob.getJob_type());

            Job savedJob = jobRepository.save(job);
            programJobRepository.addJobToProgram(programId, savedJob.getId());

        }
    }

    @Override
    public void generateOutcome(Integer programId) {
        Program program = repository.findById(programId).orElseThrow(EntityNotFoundException::new);

        String baseUrl = "https://showpiece-edging-landscape.ngrok-free.dev/learning_outcomes";

        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String input = program.getCodeName() +". "+program.getEduGoalEn();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("program", List.of(input));
        requestBody.put("token", 3500);
        requestBody.put("lang", "en");

        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpEntity<String> postEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<String> postResponse;
        try {
            postResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, postEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return;
        }
        try {

            List<LearningOutcome> outcomes =
                    objectMapper.readValue(
                            postResponse.getBody(),
                            new TypeReference<List<LearningOutcome>>() {}
                    );
            AtomicInteger counter = new AtomicInteger(1);
            List<LearningOutcome> entities = outcomes.stream()
                    .map(outcome -> {
                        LearningOutcome entity = new LearningOutcome();
                        entity.setCode("N" + counter.getAndIncrement());
                        entity.setLearningOutcomeEn(
                                outcome.getLearningOutcomeEn()
                        );

                        entity.setProgram(program);
                        learningOutcomeRepository.save(entity);

                        return entity;
                    })
                    .toList();



        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void generateCourseDescription(Integer courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);

        String baseUrl = "https://showpiece-edging-landscape.ngrok-free.dev/course_description";

        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String input = course.getNameEn();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("program", List.of(input));
        requestBody.put("token", 1000);
        requestBody.put("lang", "en");

        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpEntity<String> postEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<String> postResponse;
        try {
            postResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, postEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(postResponse.getBody());

        course.setBriefInfoEn(postResponse.getBody());
        courseRepository.save(course);
    }

    public void generateCourseProgram(Integer programId) {
        Program program = repository.findById(programId).orElseThrow(EntityNotFoundException::new);
        Integer user_id = repository.findOwnerByProgramId(programId);
        User user= userRepository.findById(user_id).orElseThrow(EntityNotFoundException::new);

        String baseUrl = "https://showpiece-edging-landscape.ngrok-free.dev/courseProgram";

        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String input = program.getCodeName();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("program", List.of(input));
        requestBody.put("token", 400);
        requestBody.put("lang", "en");

        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpEntity<String> postEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<String> postResponse;
        try {
            postResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, postEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return;
        }

        String response = postResponse.getBody();

        if (response == null || response.isBlank()) {
            return;
        }

        // Remove [ and ]
        response = response.trim();

        if (response.startsWith("[") && response.endsWith("]")) {
            response = response.substring(1, response.length() - 1);
        }

        // Split courses by ;
        List<String> courseNames = Arrays.stream(response.split(";"))
                .map(String::trim)
                .filter(name -> !name.isBlank())
                .distinct()
                .toList();

        List<CourseProgram> coursePrograms = new ArrayList<>();

        for (String courseName : courseNames) {

            // Find existing course or create new one
            Course course = courseRepository
                    .findByNameEnIgnoreCase(courseName)
                    .orElseGet(() -> {
                        Course newCourse = new Course();
                        newCourse.setNameEn(courseName);
                        newCourse.setSelective(false);

                        return courseRepository.save(newCourse);
                    });

            CourseProgram courseProgram = new CourseProgram();

            courseProgram.setProgram(program);
            courseProgram.setCourse(course);

            courseProgram.setYear(1);
            courseProgram.setTerm(1);
            courseProgram.setCreditCount(5);

            coursePrograms.add(courseProgram);

            CourseUser courseUser = new CourseUser();
            courseUser.setUser(user);
            courseUser.setCourse(course);
            courseUser.setAssignedAt(LocalDateTime.now());
            courseUserRepository.save(courseUser);
        }

        courseProgramRepository.saveAll(coursePrograms);
    }

    /**
     * Retrieves dashboard data for a specific program
     * @param programId The ID of the program
     * @return Map containing program-specific dashboard data
     */
    public Map<String, Object> getProgramDashboardData(Integer programId) {
        Map<String, Object> response = new HashMap<>();

        // Add program-specific dashboard data
        // Here we would need to create specific methods for program-level data
        // This is a placeholder for future implementation

        return response;
    }

    /**
     * 1. Programs by Academic Degree
     */
    public List<Map<String, Object>> getProgramsByAcademicDegree() {
        List<Object[]> results = repository.countProgramsByAcademicDegree();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("academicDegree", row[0]);
            item.put("count", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 2. Programs by ISCED/NQF/SQF Levels
     */
    public Map<String, List<Map<String, Object>>> getProgramsByEducationalLevels() {
        Map<String, List<Map<String, Object>>> response = new HashMap<>();

        // ISCED Levels
        List<Object[]> iscedResults = repository.countProgramsByIscedLevel();
        List<Map<String, Object>> iscedData = new ArrayList<>();

        for (Object[] row : iscedResults) {
            Map<String, Object> item = new HashMap<>();
            item.put("level", row[0]);
            item.put("count", row[1]);
            iscedData.add(item);
        }
        response.put("isced", iscedData);

        // NQF Levels
        List<Object[]> nqfResults = repository.countProgramsByNqfLevel();
        List<Map<String, Object>> nqfData = new ArrayList<>();

        for (Object[] row : nqfResults) {
            Map<String, Object> item = new HashMap<>();
            item.put("level", row[0]);
            item.put("count", row[1]);
            nqfData.add(item);
        }
        response.put("nqf", nqfData);

        // SQF Levels
        List<Object[]> sqfResults = repository.countProgramsBySqfLevel();
        List<Map<String, Object>> sqfData = new ArrayList<>();

        for (Object[] row : sqfResults) {
            Map<String, Object> item = new HashMap<>();
            item.put("level", row[0]);
            item.put("count", row[1]);
            sqfData.add(item);
        }
        response.put("sqf", sqfData);

        return response;
    }

    /**
     * 3. Selective vs Required Courses
     */
    public List<Map<String, Object>> getCoursesBySelectiveStatus() {
        List<Object[]> results = repository.countCoursesBySelectiveStatus();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("isSelective", row[0]);
            item.put("count", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 4. Course Distribution by Term
     */
    public List<Map<String, Object>> getCourseDistributionByTerm() {
        List<Object[]> results = repository.countCoursesByTermAndYear();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("year", row[0]);
            item.put("term", row[1]);
            item.put("courseCount", row[2]);
            response.add(item);
        }

        return response;
    }

    /**
     * 5. Credit Distribution
     */
    public List<Map<String, Object>> getCreditDistribution() {
        List<Object[]> results = repository.averageCreditsByTermAndYear();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("year", row[0]);
            item.put("term", row[1]);
            item.put("averageCredits", row[2]);
            response.add(item);
        }

        return response;
    }

    /**
     * 6. User Activity Timeline
     */
    public List<Map<String, Object>> getUserActivityTimeline() {
        List<Object[]> results = repository.userLoginsByDate();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("date", row[0]);
            item.put("loginCount", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 7. Program Participation
     */
    public List<Map<String, Object>> getProgramParticipation() {
        List<Object[]> results = repository.countUsersByProgram();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programId", row[0]);
            item.put("programName", row[1]);
            item.put("userCount", row[2]);
            response.add(item);
        }

        return response;
    }

    /**
     * 8. Course Assignment Distribution
     */
    public List<Map<String, Object>> getCourseAssignmentDistribution() {
        List<Object[]> results = repository.countUsersByCourse();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("courseId", row[0]);
            item.put("courseCode", row[1]);
            item.put("courseName", row[2]);
            item.put("userCount", row[3]);
            response.add(item);
        }

        return response;
    }

    /**
     * 9. Learning Outcomes per Program
     */
    public List<Map<String, Object>> getLearningOutcomesPerProgram() {
        List<Object[]> results = repository.countLearningOutcomesByProgram();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programId", row[0]);
            item.put("programName", row[1]);
            item.put("outcomeCount", row[2]);
            response.add(item);
        }

        return response;
    }

    /**
     * 10. Jobs by Program
     */
    public List<Map<String, Object>> getJobsByProgram() {
        List<Object[]> results = repository.countJobsByProgram();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programId", row[0]);
            item.put("programName", row[1]);
            item.put("jobCount", row[2]);
            response.add(item);
        }

        return response;
    }

    /**
     * 11. Job Type Distribution
     */
    public List<Map<String, Object>> getJobTypeDistribution() {
        List<Object[]> results = repository.countJobsByType();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("jobType", row[0]);
            item.put("count", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 12. Program Health Dashboard
     */
    public List<Map<String, Object>> getProgramHealthMetrics() {
        List<Object[]> results = repository.getProgramHealthMetrics();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programId", row[0]);
            item.put("programName", row[1]);
            item.put("learningOutcomes", row[2]);
            item.put("jobs", row[3]);
            item.put("standards", row[4]);
            item.put("recommendations", row[5]);
            response.add(item);
        }

        return response;
    }

    /**
     * 13. Course Prerequisites Network
     */
    public List<Map<String, Object>> getCoursePrerequisitesNetwork() {
        List<Object[]> results = repository.getCoursePrerequisitesNetwork();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("courseId", row[0]);
            item.put("courseCode", row[1]);
            item.put("courseName", row[2]);
            item.put("prerequisites", row[3]);
            response.add(item);
        }

        return response;
    }

    /**
     * 14. Recent Program Activity
     */
    public List<Map<String, Object>> getRecentProgramActivity() {
        List<Object[]> results = repository.getRecentProgramActivity();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programName", row[0]);
            item.put("userCount", row[1]);
            item.put("createdDate", row[2]);
            response.add(item);
        }

        return response;
    }

    /**
     * 15. Program Completeness
     */
    public List<Map<String, Object>> getProgramCompleteness() {
        List<Object[]> results = repository.getProgramCompleteness();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programId", row[0]);
            item.put("programName", row[1]);
            item.put("hasLearningOutcomes", row[2]);
            item.put("hasJobs", row[3]);
            item.put("hasStandards", row[4]);
            item.put("hasRecommendations", row[5]);

            // Calculate completeness percentage
            int completeness = 0;
            int total = 4; // Total number of components

            for (int i = 2; i <= 5; i++) {
                completeness += ((Integer) row[i]).intValue();
            }

            double completenessPercentage = (completeness / (double) total) * 100;
            item.put("completenessPercentage", completenessPercentage);

            response.add(item);
        }

        return response;
    }

    /**
     * 16. Course Language Distribution
     */
    public Map<String, Object> getCourseLanguageDistribution() {
        Object[] result = (Object[]) repository.getCourseLanguageDistribution();
        result = (Object[]) result[0];
        Map<String, Object> response = new HashMap<>();
        System.out.println(result.length);
        response.put("kazakh", result[0]);
        response.put("russian", result[1]);
        response.put("english", result[2]);

        return response;
    }

    /**
     * 17. Top Standards Used
     */
    public List<Map<String, Object>> getTopStandardsUsed() {
        List<Object[]> results = repository.getTopStandardsUsed();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("standardName", row[0]);
            item.put("programCount", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 18. Most Assigned Courses
     */
    public List<Map<String, Object>> getMostAssignedCourses() {
        List<Object[]> results = repository.getMostAssignedCourses();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("courseCode", row[0]);
            item.put("courseName", row[1]);
            item.put("assignedCount", row[2]);
            response.add(item);
        }

        return response;
    }

    /**
     * 19. Program Credits Summary
     */
    public List<Map<String, Object>> getProgramCreditsSummary() {
        List<Object[]> results = repository.getProgramCreditsSummary();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programName", row[0]);
            item.put("totalCredits", row[1]);
            item.put("assignedCredits", row[2] != null ? row[2] : 0);

            // Calculate completion percentage
            Integer totalCredits = (Integer) row[1];
            BigInteger big = (BigInteger) row[2];
            Integer assignedCredits = big != null ? big.intValue() : 0;
            double completionPercentage = 0;

            if (totalCredits > 0) {
                completionPercentage = (assignedCredits / (double) totalCredits) * 100;
            }

            item.put("completionPercentage", completionPercentage);
            response.add(item);
        }

        return response;
    }

    /**
     * 20. User Roles Distribution
     */
    public List<Map<String, Object>> getUserRolesDistribution() {
        List<Object[]> results = repository.getUserRolesDistribution();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("roleId", row[0]);
            item.put("userCount", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 21. Programs by Direction
     */
    public List<Map<String, Object>> getProgramsByDirection() {
        List<Object[]> results = repository.getProgramsByDirection();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("directionCodeName", row[0]);
            item.put("programCount", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 22. Course Programs Timeline
     */
    public List<Map<String, Object>> getCoursesProgramsTimeline() {
        List<Object[]> results = repository.getCoursesProgramsTimeline();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("year", row[0]);
            item.put("courseCount", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 23. User Creation Timeline
     */
    public List<Map<String, Object>> getUserCreationTimeline() {
        List<Object[]> results = repository.getUserCreationTimeline();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", row[0]);
            item.put("userCount", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * 24. Learning Outcomes Language Completeness
     */
    public List<Map<String, Object>> getLearningOutcomesLanguageCompleteness() {
        List<Object[]> results = repository.getLearningOutcomesLanguageCompleteness();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programName", row[0]);
            item.put("kazakhCount", row[1]);
            item.put("russianCount", row[2]);
            item.put("englishCount", row[3]);

            // Calculate total and completeness percentages
            int total = 0;
            if (row[1] != null) total += ((Number) row[1]).intValue();
            if (row[2] != null) total += ((Number) row[2]).intValue();
            if (row[3] != null) total += ((Number) row[3]).intValue();

            if (total > 0) {
                double kazakhPercentage = row[1] != null ? (((Number) row[1]).intValue() / (double) total) * 100 : 0;
                double russianPercentage = row[2] != null ? (((Number) row[2]).intValue() / (double) total) * 100 : 0;
                double englishPercentage = row[3] != null ? (((Number) row[3]).intValue() / (double) total) * 100 : 0;

                item.put("kazakhPercentage", kazakhPercentage);
                item.put("russianPercentage", russianPercentage);
                item.put("englishPercentage", englishPercentage);
            } else {
                item.put("kazakhPercentage", 0);
                item.put("russianPercentage", 0);
                item.put("englishPercentage", 0);
            }

            response.add(item);
        }

        return response;
    }

    /**
     * 25. Course Duration Summary
     */
    public List<Map<String, Object>> getCourseDurationSummary() {
        List<Object[]> results = repository.getCourseDurationSummary();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("programName", row[0]);
            item.put("durationYears", row[1]);
            item.put("courseCount", row[2]);

            // Calculate courses per year
            if (row[1] != null && ((Number) row[1]).intValue() > 0) {
                double coursesPerYear = row[2] != null ?
                        ((Number) row[2]).doubleValue() / ((Number) row[1]).doubleValue() : 0;
                item.put("coursesPerYear", coursesPerYear);
            } else {
                item.put("coursesPerYear", 0);
            }

            response.add(item);
        }

        return response;
    }
}
