package love.dragonist.classaide.controller;

import com.google.gson.JsonObject;
import love.dragonist.classaide.bean.Record;
import love.dragonist.classaide.bean.Summary;
import love.dragonist.classaide.repository.RecordRepository;
import love.dragonist.classaide.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    HttpSession session;
    @Autowired
    SummaryRepository summaryRepository;
    @Autowired
    RecordRepository recordRepository;

    /**
     * 在table页面里获取课程记录简介
     *
     * @return json记录
     */
    @GetMapping("/getAllCourses")
    public String getAllCourses() {
        String id = (String) session.getAttribute("id");
        if (id == null) return "";
        List<Summary> summaries = summaryRepository.findAllByTeacher(id);

        JsonObject object = new JsonObject();
        int i = 0;
        for (Summary summary : summaries) {
            JsonObject object1 = new JsonObject();
            int tmp = i % 8;
            i = i + 1;
            switch (tmp) {
                case 1:
                    object1.addProperty("style", "table-warning");
                    break;
                case 3:
                    object1.addProperty("style", "table-success");
                    break;
                case 5:
                    object1.addProperty("style", "table-danger");
                    break;
                case 7:
                    object1.addProperty("style", "table-info");
                    break;
                default:
                    object1.addProperty("style", "table");
            }
            object1.addProperty("courseName", summary.getCourseName());
            object1.addProperty("date", summary.getDate().toString());
            object1.addProperty("location", summary.getLocation());
            object1.addProperty("finalScore", summary.getFinalScore());
            object1.addProperty("summaryId", summary.getId());
            object.add(summary.getId() + "", object1);
        }
        return object.toString();
    }

    @PostMapping("/teaAbnormal")
    public String TeaAbnormal(@RequestParam Long id) {
        ArrayList<Record> teaRecords = recordRepository.findByLinkSummaryAndWho(id, "self");
        return getRecords(teaRecords);
    }

    @PostMapping("/stuAbnormal")
    public String StuAbnormal(@RequestParam Long id) {
        ArrayList<Record> teaRecords = recordRepository.findByLinkSummaryAndWho(id, "student");
        return getRecords(teaRecords);
    }

    private String getRecords(ArrayList<Record> teaRecords) {
        JsonObject object = new JsonObject();
        for (Record record : teaRecords) {
            JsonObject object1 = new JsonObject();
            object1.addProperty("time", record.getTime().toString());
            object1.addProperty("description", record.getDescription());
            object1.addProperty("screenshot", record.getScreenshot());
            object.add(String.valueOf(record.getId()), object1);
        }

        return object.toString();
    }
}
