package kz.com.SerAya.repository;

import kz.com.SerAya.entity.ProgramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProgramUserRepository extends JpaRepository<ProgramUser, Integer> {
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM program_user WHERE program_id = :programId and user_id = :userId", nativeQuery = true)
    void removeProgramFromUser(Integer userId, Integer programId);

}
