package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Job;
import kz.com.SerAya.entity.Skill;
import kz.com.SerAya.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    @Query(
            value = " SELECT * from job where program_id =?",
            nativeQuery = true)
    List<Skill> findSkillsByProgram(int id);
}
