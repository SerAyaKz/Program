package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProgramJobRepository extends JpaRepository<Program, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO program_job (program_id, job_id) VALUES (:programId, :jobId)", nativeQuery = true)
    void addJobToProgram( Integer programId,  Integer jobId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM program_job WHERE job_id = :jobId", nativeQuery = true)
    void removeJobFromProgram( Integer jobId);
}