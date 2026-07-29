package kz.com.SerAya.repository;

import kz.com.SerAya.entity.UserProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgramRepository extends JpaRepository<UserProgram, Integer> {
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_programs WHERE program_id = :programId and user_id = :userId", nativeQuery = true)
    void removeProgramFromUser(Integer userId, Integer programId);

    List<UserProgram> findByUserId(Integer userId);

    boolean existsByUserIdAndProgramId(Integer userId, Integer programId);

    Optional<UserProgram> findFirstByProgramIdOrderByJoinedDateAsc(Integer programId);
}
