package love.dragonist.classaide.controller;

import com.google.gson.JsonObject;
import love.dragonist.classaide.bean.Student;
import love.dragonist.classaide.bean.Takes;
import love.dragonist.classaide.repository.StudentRepository;
import love.dragonist.classaide.repository.TakesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    TakesRepository takesRepository;
    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/nameList")
    public String nameList() {
        //作弊 2
        JsonObject object = new JsonObject();
        ArrayList<Takes> takes = takesRepository.findAllByCourseId(2);
        for (Takes take : takes) {
            Student student = studentRepository.getOne(take.getId());
            JsonObject object1 = new JsonObject();
            object1.addProperty("name", student.getName());
            object.add(String.valueOf(student.getId()), object1);
        }

        return object.toString();
    }
}
