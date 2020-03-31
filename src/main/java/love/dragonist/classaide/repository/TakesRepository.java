package love.dragonist.classaide.repository;

import love.dragonist.classaide.bean.Takes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TakesRepository extends JpaRepository<Takes, Integer> {
    public ArrayList<Takes> findAllByCourseId(Integer id);
}
