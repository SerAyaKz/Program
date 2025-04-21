package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Program;
import kz.com.SerAya.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
    @Query(
            value = "SELECT program.* \n" +
                    "FROM program inner join program_user " +
                    "on program.id=program_user.program_id\n" +
                    "WHERE program_user.user_id = ?1",
            nativeQuery = true)
    List<Program> findAllByUser(int id);
}