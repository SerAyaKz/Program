package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Job;
import kz.com.SerAya.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    @Query(
            value = " SELECT * from skill where job_id =?",
            nativeQuery = true)
    Skill findAllByJob_Id(int id);

    List<Skill> findByProgramIdOrderByYearRangeDescFreqDesc(Integer programId);
    void deleteByProgramId(Integer programId);

    List<Skill> findByProgram_Id(Integer programId);
}
