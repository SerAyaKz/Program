package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    @Query(
            value = " SELECT * from section where program_id =? order by numbering",
            nativeQuery = true)
    List<Section> findSectionsByProgram(int id);
}