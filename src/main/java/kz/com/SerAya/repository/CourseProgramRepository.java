package kz.com.SerAya.repository;

import kz.com.SerAya.entity.CourseProgram;
import kz.com.SerAya.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CourseProgramRepository extends JpaRepository<CourseProgram, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO program_job (program_id, job_id) VALUES (:programId, :jobId)", nativeQuery = true)
    void addJobToProgram( Integer programId,  Integer jobId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM program_job WHERE job_id = :jobId", nativeQuery = true)
    void removeJobFromProgram( Integer jobId);

    @Query(
            value = "SELECT * \n" +
                    "FROM course_program \n" +
                    "WHERE program_id = ?",
            nativeQuery = true)
    List<CourseProgram> findCourseProgramsByProgram(int id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM course_program WHERE program_id = :id", nativeQuery = true)
    void deleteByProgram( Integer id);
}
