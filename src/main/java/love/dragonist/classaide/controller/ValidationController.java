package love.dragonist.classaide.controller;

import com.google.gson.JsonObject;
import love.dragonist.classaide.bean.Teacher;
import love.dragonist.classaide.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping(value = "/validation")
public class ValidationController {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    HttpSession session;

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpServletResponse response) {
        JsonObject object = new JsonObject();

        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if (!teacherOptional.isPresent()) {
            object.addProperty("message", "教师工号错误");
        } else if (!teacherOptional.get().getPassword().equals(password)) {
            object.addProperty("message", "密码错误");
        } else {
            object.addProperty("message", "success");
            session.setAttribute("id", id);
            session.setAttribute("name", teacherOptional.get().getName());
        }

        return object.toString();
    }
}
