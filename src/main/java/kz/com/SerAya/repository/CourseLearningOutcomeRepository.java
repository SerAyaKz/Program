package kz.com.SerAya.repository;

import kz.com.SerAya.entity.CourseLearningOutcome;
import kz.com.SerAya.entity.CourseProgram;
import kz.com.SerAya.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface CourseLearningOutcomeRepository extends JpaRepository<CourseLearningOutcome, Integer> {
    List<CourseLearningOutcome> findByCourse_IdIn(Collection<Integer> courseIds);
    List<CourseLearningOutcome> findByCourse_IdAndLearningOutcome_CodeIn(Integer courseId, Collection<String> codes);
    List<CourseLearningOutcome> findByProgram_Id(Integer programId);

}
