package love.dragonist.classaide.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "teacher")
public class Teacher {
    @Id
    private String id;
    private String password;
    private String name;
}
