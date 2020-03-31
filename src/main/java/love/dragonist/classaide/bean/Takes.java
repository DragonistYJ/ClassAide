package love.dragonist.classaide.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "takes")
public class Takes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer courseId;
}
