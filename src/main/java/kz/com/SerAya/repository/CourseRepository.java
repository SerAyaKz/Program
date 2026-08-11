package kz.com.SerAya.repository;

import kz.com.SerAya.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(
            value = "SELECT course.* \n" +
                    "FROM course inner join course_user " +
                    "on course.id=course_user.course_id\n" +
                    "WHERE course_user.user_id = ?1",
            nativeQuery = true)
    List<Course> findAllByUser(int id);

    @Query(
            value = "SELECT course.*\n" +
                    "                    FROM course inner join course_user \n" +
                    "                    on course.id=course_user.course_id\n" +
                    "                    WHERE course_user.user_id in (\t\t\t\t\n" +
                    "\t\t\t\t\tselect user_id from user_programs where program_id=?1 )",
            nativeQuery = true)
    List<Course> findAllProgramByUser(int id);

    Optional<Course> findByNameEnIgnoreCase(String courseName);
}
