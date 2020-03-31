package love.dragonist.classaide.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course")
public class Course {
    //课程号
    private String no;
    //课序号
    private String serial;
    private String name;
    private Integer week;
    private Integer courseBegin;
    private Integer courseEnd;
    private String teacher;
    private String classroom;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
