package kz.com.SerAya.Repository;

import kz.com.SerAya.Entity.Section;
import kz.com.SerAya.Entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Integer> {
    @Query(
            value = " SELECT * from standard where program_id =?",
            nativeQuery = true)
    List<Standard> findStandardsByProgram(int id);
}
