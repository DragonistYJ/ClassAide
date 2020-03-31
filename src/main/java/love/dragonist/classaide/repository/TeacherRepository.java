package love.dragonist.classaide.repository;

import love.dragonist.classaide.bean.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
}
