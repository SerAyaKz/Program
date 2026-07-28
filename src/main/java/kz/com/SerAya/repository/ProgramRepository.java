package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
    @Query(
            value = "SELECT programs.* \n" +
                    "FROM programs inner join user_programs " +
                    "on programs.id=user_programs.program_id\n" +
                    "WHERE user_programs.user_id = ?1",
            nativeQuery = true)
    List<Program> findAllByUser(int id);

    @Query(value = "SELECT academic_degree, COUNT(*) as count FROM programs GROUP BY academic_degree", nativeQuery = true)
    List<Object[]> countProgramsByAcademicDegree();

    // 2. Programs by ISCED/NQF/SQF Levels
    @Query(value = "SELECT isced_level, COUNT(*) as count FROM programs WHERE isced_level IS NOT NULL GROUP BY isced_level", nativeQuery = true)
    List<Object[]> countProgramsByIscedLevel();

    @Query(value = "SELECT nqf_level, COUNT(*) as count FROM programs GROUP BY nqf_level", nativeQuery = true)
    List<Object[]> countProgramsByNqfLevel();

    @Query(value = "SELECT sqf_level, COUNT(*) as count FROM programs GROUP BY sqf_level", nativeQuery = true)
    List<Object[]> countProgramsBySqfLevel();

    // 3. Selective vs Required Courses
    @Query(value = "SELECT is_selective, COUNT(*) as count FROM course GROUP BY is_selective", nativeQuery = true)
    List<Object[]> countCoursesBySelectiveStatus();

    // 4. Course Distribution by Term
    @Query(value = "SELECT year, term, COUNT(*) as course_count FROM course_program GROUP BY year, term ORDER BY year, term", nativeQuery = true)
    List<Object[]> countCoursesByTermAndYear();

    // 5. Credit Distribution
    @Query(value = "SELECT year, term, AVG(credit_count) as avg_credits FROM course_program GROUP BY year, term ORDER BY year, term", nativeQuery = true)
    List<Object[]> averageCreditsByTermAndYear();

    // 6. User Activity Timeline
    @Query(value = "SELECT DATE(last_login) as login_date, COUNT(*) as login_count FROM users GROUP BY DATE(last_login) ORDER BY login_date", nativeQuery = true)
    List<Object[]> userLoginsByDate();

    // 7. Program Participation
    @Query(value = "SELECT p.id, p.code_name, COUNT(pu.user_id) as user_count FROM programs p LEFT JOIN user_programs pu ON p.id = pu.program_id GROUP BY p.id, p.code_name ORDER BY user_count DESC", nativeQuery = true)
    List<Object[]> countUsersByProgram();

    // 8. Course Assignment Distribution
    @Query(value = "SELECT c.id, c.code, c.name_en, COUNT(cu.user_id) as user_count FROM course c LEFT JOIN course_user cu ON c.id = cu.course_id GROUP BY c.id, c.code, c.name_en ORDER BY user_count DESC", nativeQuery = true)
    List<Object[]> countUsersByCourse();

    // 9. Learning Outcomes per Program
    @Query(value = "SELECT p.id, p.code_name, COUNT(lo.id) as outcome_count FROM programs p LEFT JOIN learning_outcomes lo ON p.id = lo.program_id GROUP BY p.id, p.code_name ORDER BY outcome_count DESC", nativeQuery = true)
    List<Object[]> countLearningOutcomesByProgram();

    // 10. Jobs by Program
    @Query(value = "SELECT p.id, p.code_name, COUNT(pj.job_id) as job_count FROM programs p LEFT JOIN program_job pj ON p.id = pj.program_id GROUP BY p.id, p.code_name ORDER BY job_count DESC", nativeQuery = true)
    List<Object[]> countJobsByProgram();

    // 11. Job Type Distribution
    @Query(value = "SELECT job_type, COUNT(*) as count FROM job WHERE job_type IS NOT NULL GROUP BY job_type", nativeQuery = true)
    List<Object[]> countJobsByType();

    // 12. Program Health Dashboard
    @Query(value = "SELECT p.id, p.code_name, " +
            "COUNT(DISTINCT lo.id) as learning_outcomes, " +
            "COUNT(DISTINCT pj.job_id) as job, " +
            "COUNT(DISTINCT ps.standard_id) as standards, " +
            "COUNT(DISTINCT r.id) as recommendation " +
            "FROM programs p " +
            "LEFT JOIN learning_outcomes lo ON p.id = lo.program_id " +
            "LEFT JOIN program_job pj ON p.id = pj.program_id " +
            "LEFT JOIN program_standard ps ON p.id = ps.program_id " +
            "LEFT JOIN recommendation r ON p.id = r.program_id " +
            "GROUP BY p.id, p.code_name", nativeQuery = true)
    List<Object[]> getProgramHealthMetrics();

    // 13. Course Prerequisites Network
    @Query(value = "SELECT c.id, c.code, c.name_en, c.prerequisites FROM course c WHERE c.prerequisites IS NOT NULL", nativeQuery = true)
    List<Object[]> getCoursePrerequisitesNetwork();

    // Additional queries for comprehensive dashboard data

    // 14. Recent Program Activity
    @Query(value = "SELECT p.code_name, COUNT(pu.user_id) as users, p.created_date " +
            "FROM programs p " +
            "LEFT JOIN user_programs pu ON p.id = pu.program_id " +
            "GROUP BY p.id, p.code_name, p.created_date " +
            "ORDER BY p.created_date DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getRecentProgramActivity();

    // 15. Program Completeness
    @Query(value = "SELECT p.id, p.code_name, " +
            "CASE WHEN COUNT(DISTINCT lo.id) > 0 THEN 1 ELSE 0 END as has_learning_outcomes, " +
            "CASE WHEN COUNT(DISTINCT pj.job_id) > 0 THEN 1 ELSE 0 END as has_job, " +
            "CASE WHEN COUNT(DISTINCT ps.standard_id) > 0 THEN 1 ELSE 0 END as has_standards, " +
            "CASE WHEN COUNT(DISTINCT r.id) > 0 THEN 1 ELSE 0 END as has_recommendation " +
            "FROM programs p " +
            "LEFT JOIN learning_outcomes lo ON p.id = lo.program_id " +
            "LEFT JOIN program_job pj ON p.id = pj.program_id " +
            "LEFT JOIN program_standard ps ON p.id = ps.program_id " +
            "LEFT JOIN recommendation r ON p.id = r.program_id " +
            "GROUP BY p.id, p.code_name", nativeQuery = true)
    List<Object[]> getProgramCompleteness();

    // 16. Course Language Distribution
    @Query(value = "SELECT " +
            "SUM(CASE WHEN name_kz IS NOT NULL AND LENGTH(name_kz) > 0 THEN 1 ELSE 0 END) as kz_count, " +
            "SUM(CASE WHEN name_ru IS NOT NULL AND LENGTH(name_ru) > 0 THEN 1 ELSE 0 END) as ru_count, " +
            "SUM(CASE WHEN name_en IS NOT NULL AND LENGTH(name_en) > 0 THEN 1 ELSE 0 END) as en_count " +
            "FROM course", nativeQuery = true)
    Object[] getCourseLanguageDistribution();

    // 17. Top Standards Used
    @Query(value = "SELECT s.name_en, COUNT(ps.program_id) as program_count " +
            "FROM standard s " +
            "JOIN program_standard ps ON s.id = ps.standard_id " +
            "GROUP BY s.id, s.name_en " +
            "ORDER BY program_count DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getTopStandardsUsed();

    // 18. Most Assigned Courses
    @Query(value = "SELECT c.code, c.name_en, COUNT(cu.user_id) as assigned_count " +
            "FROM course c " +
            "JOIN course_user cu ON c.id = cu.course_id " +
            "GROUP BY c.id, c.code, c.name_en " +
            "ORDER BY assigned_count DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getMostAssignedCourses();

    // 19. Program Credits Summary
    @Query(value = "SELECT p.code_name, p.credits_count as total_credits, " +
            "SUM(cp.credit_count) as assigned_credits " +
            "FROM programs p " +
            "LEFT JOIN course_program cp ON p.id = cp.program_id " +
            "GROUP BY p.id, p.code_name, p.credits_count", nativeQuery = true)
    List<Object[]> getProgramCreditsSummary();

    // 20. User Roles Distribution
    @Query(value = "SELECT role_id, COUNT(*) as user_count FROM users GROUP BY role_id", nativeQuery = true)
    List<Object[]> getUserRolesDistribution();

    // 21. Programs by Direction
    @Query(value = "SELECT direction_code_name, COUNT(*) as program_count FROM programs GROUP BY direction_code_name", nativeQuery = true)
    List<Object[]> getProgramsByDirection();

    // 22. Course Programs Timeline
    @Query(value = "SELECT year, COUNT(*) as course_count FROM course_program GROUP BY year ORDER BY year", nativeQuery = true)
    List<Object[]> getCoursesProgramsTimeline();

    // 23. User Creation Timeline
    @Query(value = "SELECT TO_CHAR(created_at, 'YYYY-MM') as month, COUNT(*) as user_count " +
            "FROM users " +
            "GROUP BY TO_CHAR(created_at, 'YYYY-MM') " +
            "ORDER BY month", nativeQuery = true)
    List<Object[]> getUserCreationTimeline();

    // 24. Learning Outcomes Language Completeness
    @Query(value = "SELECT p.code_name, " +
            "SUM(CASE WHEN lo.learning_outcome_kz IS NOT NULL AND LENGTH(lo.learning_outcome_kz) > 0 THEN 1 ELSE 0 END) as kz_count, " +
            "SUM(CASE WHEN lo.learning_outcome_ru IS NOT NULL AND LENGTH(lo.learning_outcome_ru) > 0 THEN 1 ELSE 0 END) as ru_count, " +
            "SUM(CASE WHEN lo.learning_outcome_en IS NOT NULL AND LENGTH(lo.learning_outcome_en) > 0 THEN 1 ELSE 0 END) as en_count " +
            "FROM programs p " +
            "LEFT JOIN learning_outcomes lo ON p.id = lo.program_id " +
            "GROUP BY p.id, p.code_name", nativeQuery = true)
    List<Object[]> getLearningOutcomesLanguageCompleteness();

    // 25. Course Duration Summary
    @Query(value = "SELECT p.code_name, p.study_duration_years, COUNT(cp.course_id) as course_count " +
            "FROM programs p " +
            "LEFT JOIN course_program cp ON p.id = cp.program_id " +
            "GROUP BY p.id, p.code_name, p.study_duration_years", nativeQuery = true)
    List<Object[]> getCourseDurationSummary();
}