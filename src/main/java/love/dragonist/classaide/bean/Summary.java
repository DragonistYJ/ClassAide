package love.dragonist.classaide.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "summary")
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private String courseName;
    private String teacher;
    @DateTimeFormat(pattern = "yy:MM:dd")
    private Date date;
    private String location;
    private double teacherScore;
    private double studentScore;
    private double finalScore;
    private String description;
}
