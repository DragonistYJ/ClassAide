package love.dragonist.classaide.repository;

import love.dragonist.classaide.bean.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    public ArrayList<Record> findByLinkSummaryAndWho(Long linkSummary, String who);
}
