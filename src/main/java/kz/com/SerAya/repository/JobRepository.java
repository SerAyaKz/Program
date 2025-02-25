package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Job;
import kz.com.SerAya.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query(
            value = " SELECT job.* \n" +
                    "FROM job \n" +
                    "INNER JOIN program_job ON job.id = program_job.job_id \n" +
                    "WHERE program_job.program_id = ?;",
            nativeQuery = true)
    List<Job> findJobsByProgram(int id);
}
