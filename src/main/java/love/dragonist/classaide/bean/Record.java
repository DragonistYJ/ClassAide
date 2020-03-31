package love.dragonist.classaide.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;

@Data
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long linkSummary;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Time time;
    private String who;
    private String description;
    private String screenshot;
}
