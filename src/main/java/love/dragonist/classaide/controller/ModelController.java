package love.dragonist.classaide.controller;

import love.dragonist.classaide.bean.Course;
import love.dragonist.classaide.bean.Summary;
import love.dragonist.classaide.bean.Teacher;
import love.dragonist.classaide.repository.CourseRepository;
import love.dragonist.classaide.repository.SummaryRepository;
import love.dragonist.classaide.repository.TeacherRepository;
import love.dragonist.classaide.util.WeekNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ModelController {
    @Autowired
    HttpSession session;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SummaryRepository summaryRepository;

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/main")
    public String main(Model model) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
        }
        model.addAttribute("teacherName", session.getAttribute("name"));
        model.addAttribute("path", "screenshot2/output.jpg");
        return "main";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }

    @GetMapping("/email")
    public String email() {
        return "email";
    }

    @GetMapping("/media")
    public String media() {
        return "media";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/rollCall")
    public String rollCall(Model model) {
        model.addAttribute("teacherName", session.getAttribute("name"));
        return "rollCall";
    }

    @GetMapping("/tables")
    public String tables(Model model) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
        }
        model.addAttribute("teacherName", session.getAttribute("name"));
        return "tables";
    }

    @GetMapping("/replay")
    public String replay(@RequestParam Long summaryId, Model model) {
        String id = (String) session.getAttribute("id");
        if (id == null) {//处理

        }

        Summary summary = summaryRepository.findById(summaryId).get();
        Course course = courseRepository.findById(summary.getCourseId()).get();
        Teacher teacher = teacherRepository.findById(course.getTeacher()).get();

        String title = "课程名称：" + course.getName() + "<br>教师：" + teacher.getName() + "<br>时间：周"
                + WeekNum.num2Chinese(course.getWeek()) + course.getCourseBegin() + "-" + course.getCourseEnd()
                + "节</br>教室：" + course.getClassroom();
        model.addAttribute("title", title);

        return "replay";
    }
}
