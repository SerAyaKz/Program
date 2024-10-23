package kz.com.SerAya.Repository;

import kz.com.SerAya.Entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    @Query(
            value = " WITH OrderedSections AS (\n" +
                    "    SELECT \n" +
                    "        *,\n" +
                    "        CASE \n" +
                    "            WHEN parent_section_id IS NULL THEN id  \n" +
                    "            ELSE parent_section_id  \n" +
                    "        END AS SortGroup\n" +
                    "    FROM \n" +
                    "        section where program_id=?\n" +
                    ")\n" +
                    "\n" +
                    "SELECT \n" +
                    "    *\n" +
                    "FROM \n" +
                    "    OrderedSections \n" +
                    "ORDER BY \n" +
                    "    SortGroup, \n" +
                    "    hierarchy_level,  \n" +
                    "    position_in_program;",
            nativeQuery = true)
    List<Section> findSectionsByProgram(int id);
}