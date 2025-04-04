package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Standard;
import kz.com.SerAya.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Integer> {
    @Query(
            value = " SELECT standard.* \n" +
                    "FROM standard \n" +
                    "INNER JOIN program_standard ON standard.id = program_standard.standard_id \n" +
                    "WHERE program_standard.program_id = ?;",
            nativeQuery = true)
    List<Standard> findStandardsByProgram(int id);
}
