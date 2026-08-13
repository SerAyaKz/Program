package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProgramStandardRepository extends JpaRepository<Program, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO program_standard (program_id, standard_id) VALUES (:programId, :standardId)", nativeQuery = true)
    void addStandardToProgram( Integer programId,  Integer standardId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM program_standard WHERE standard_id = :standardId", nativeQuery = true)
    void removeStandardFromProgram( Integer standardId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM program_standard WHERE program_id = :id", nativeQuery = true)
    void deleteAllByProgram_Id(Integer id);
}