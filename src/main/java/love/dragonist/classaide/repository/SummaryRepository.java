package love.dragonist.classaide.repository;

import love.dragonist.classaide.bean.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Long> {
    public List<Summary> findAllByTeacher(String teacher);
}
