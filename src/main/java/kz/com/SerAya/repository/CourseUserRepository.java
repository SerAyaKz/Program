package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Course;
import kz.com.SerAya.entity.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM course_user WHERE course_id = :courseId and user_id = :userId", nativeQuery = true)
    void removeCourseFromUser(Integer userId, Integer courseId);

}
