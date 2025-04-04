package kz.com.SerAya.repository;

import kz.com.SerAya.entity.LearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LearningOutcomeRepository extends JpaRepository<LearningOutcome, Integer> {
    @Query(
            value = " SELECT * \n" +
                    "FROM learning_outcomes \n" +
                    "WHERE program_id = ?;",
            nativeQuery = true)
    List<LearningOutcome> findLearningOutcomesByProgram(int id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM learning_outcomes WHERE program_id = :id", nativeQuery = true)
    void deleteByProgram( Integer id);

}
