package love.dragonist.classaide.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "student")
public class Student {
    @Id
    private Integer id;
    private String name;
    private String college;
    private Integer grade;
}
