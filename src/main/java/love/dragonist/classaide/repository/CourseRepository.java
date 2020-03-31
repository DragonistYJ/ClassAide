package love.dragonist.classaide.repository;

import love.dragonist.classaide.bean.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c where c.teacher=?1 and c.week=?2 and c.courseBegin<=?3 and c.courseEnd>=?3")
    public Course findRealCourse(String teacher, Integer week, Integer time);

    public Course findByNoAndSerial(String no, String serial);
}
