package love.dragonist.classaide.controller;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.baidu.aip.nlp.AipNlp;
import com.google.gson.Gson;
import love.dragonist.classaide.Beans.*;
import love.dragonist.classaide.EasyDL.client.EasyVoice;
import love.dragonist.classaide.EasyDL.recognition.Words;
import love.dragonist.classaide.pandleinterface.PictureUtil;
import love.dragonist.classaide.repository.CourseRepository;
import love.dragonist.classaide.util.BaiDuAPI;
import love.dragonist.classaide.util.Label;
import love.dragonist.classaide.util.Media;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController()
@RequestMapping("/realTime")
public class RealTimeController {
    @Autowired
    private EasyVoice easyVoice;
    @Autowired
    HttpSession session;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private Words words;
    @Autowired
    private BaiDuAPI baiDuAPI;
    @Autowired
    private AipBodyAnalysis aipBodyAnalysis;
    @Autowired
    private AipNlp aipNlp;

    private Logger logger = LoggerFactory.getLogger(RealTimeController.class);
    private int cutTime = 0;
    private FFmpegFrameGrabber ffmpegFrameGrabber;

    //课程讲解相似度
    private double similarity = -1;
    //教师讲课语速
    private int speechSpeed = -1;
    //教师当前情绪
    private String emotion = null;

    @PostMapping("/studentStatus")
    public String studentStatus() {
        String id = (String) session.getAttribute("id");
        if (id == null) return "fail";

        JSONObject numResult = baiDuAPI.studentNum("D:\\Cache\\ShotCache\\shot.jpg");
        JSONArray studentNum = numResult.getJSONArray("results");
        int sumNum = studentNum.length();

        int sleepNum = 0;
        int phoneNum = 0;
        JSONObject statusResult = baiDuAPI.studentStatusDetect("D:\\Cache\\ShotCache\\shot.jpg");
        logger.info(statusResult.toString());
        JSONArray status = statusResult.getJSONArray("results");
        for (int i = 0; i < status.length(); i++) {
            JSONObject object = status.getJSONObject(i);
            if (object.getString("name").equals("sleep")) sleepNum += 1;
            else phoneNum += 1;
        }

        Gson gson = new Gson();
        ReturnBean returnBean = gson.fromJson(statusResult.toString(), ReturnBean.class);
        List<InfoStud> infoStuds = returnBean.getResults();
        PictureUtil.circleStudent("D:\\Cache\\ShotCache\\shot.jpg", "D:\\Cache\\ShotCache\\output.jpg", infoStuds);

        SumInfo sumInfo = this.locationCalculate(numResult.toString(), statusResult.toString());
        sumInfo.setNumber(sumNum - sleepNum - phoneNum);
        try {
            sumInfo.setBase64(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get("D:\\Cache\\ShotCache\\output.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(gson.toJson(sumInfo));


//        JSONObject result = new JSONObject();
//        result.put("sumNum", sumNum - sleepNum - phoneNum);
//        result.put("sleepNum", sleepNum);
//        result.put("phoneNum", phoneNum);
//        try {
//            result.put("image", Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get("D:\\Cache\\ShotCache\\output.jpg"))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return gson.toJson(sumInfo);
    }

    public SumInfo locationCalculate(String numResult, String statusResult) {
        SumInfo sumInfo = new SumInfo();
        List<List<InfoStud>> list = preProcess(numResult);
        List<XY> phoneXy = new ArrayList<>();
        List<XY> sleepXy = new ArrayList<>();
        int sleep = 0;
        int phone = 0;
        ReturnBean statusInfo = new Gson().fromJson(statusResult, ReturnBean.class);
        for (InfoStud infoStud : statusInfo.getResults()) {
            if (infoStud.getName().equals("sleep")) {
                sleepXy.add(chooseSimilarest(list, infoStud));
                sleep++;
            } else if (infoStud.getName().equals("phone")) {
                sleepXy.add(chooseSimilarest(list, infoStud));
                phone++;
            }
        }
        sumInfo.setSleep(sleep);
        sumInfo.setPhone(phone);
        sumInfo.setPhoneXy(phoneXy);
        sumInfo.setSleepXy(sleepXy);
        return sumInfo;
    }

    public List<List<InfoStud>> preProcess(String strResult) {
        ReturnBean returnBean = new Gson().fromJson(strResult, ReturnBean.class);
        List<InfoStud> studList = returnBean.getResults();
        Collections.sort(studList);
        List<InfoStud> row = new ArrayList<>();
        List<List<InfoStud>> lists = new ArrayList<>();
        int index = 0;
        int standard = 180;
        int number = 1;
        for (int i = 0; i < studList.size(); i++) {
            if ((studList.get(index).getLocation().getTop() - studList.get(i).getLocation().getTop() < standard) && row.size() < 10) {
                row.add(studList.get(i));
            } else {
                lists.add(row);
                row = new ArrayList<>();
                row.add(studList.get(i));
                index = i;
                number++;
                standard -= 37;
            }
        }
        for (List<InfoStud> infoStudList : lists) {
            Collections.sort(infoStudList, new SortByLeft());
        }
        return lists;
    }

    public double calculateLength(InfoStud stud1, InfoStud stud2) {
        float x = stud1.getLocation().getLeft() - stud1.getLocation().getLeft();
        float y = stud1.getLocation().getTop() - stud2.getLocation().getTop();
        x = x > 0 ? x : -x;
        y = y > 0 ? y : -y;
        return Math.sqrt(x * x + y * y);
    }

    public XY chooseSimilarest(List<List<InfoStud>> lists, InfoStud infoStud) {
        XY xy = new XY(1, 1);
        int x = 1, y = 1;
        double minLength = calculateLength(lists.get(0).get(0), infoStud);
        for (List<InfoStud> studList : lists) {
            x = 1;
            for (InfoStud stud : studList) {
                double tempValue = calculateLength(stud, infoStud);
                if (tempValue < minLength) {
                    minLength = tempValue;
                    xy.setX(x);
                    xy.setY(y);
                }
                x++;
            }
            y++;
        }
        return xy;
    }

    /**
     * 计算老师讲解的课程相似度
     *
     * @return 相似度值
     */
    @PostMapping("/similarity")
    public String similarity() {
        String id = (String) session.getAttribute("id");
        if (id == null) return "请重新登录";

        //this.similarity = words.getSimilarity(words.getPPT(), words.getSpeech("E:\\voice.m4a"));
        JSONObject result = aipNlp.topic("形教课", words.getSpeech("E:\\voice.m4a"), new HashMap<>()).getJSONObject("item");

        double avgScore = 0;
        JSONArray lv1_tag_list = result.getJSONArray("lv1_tag_list");
        for (int i = 0; i < lv1_tag_list.length(); i++) {
            JSONObject object = lv1_tag_list.getJSONObject(i);
            double score = object.getDouble("score");
            String tag = object.getString("tag");
            switch (tag) {
                case "教育":
                    avgScore = score;
                    break;
                case "社会":
                    avgScore = score * 0.9;
                    break;
                case "综合":
                    avgScore = score * 0.8;
                    break;
            }
        }


        logger.info("课程粘度：" + String.valueOf(avgScore));
        return String.valueOf(avgScore * 100);
    }

    /**
     * 监测老师的语速
     *
     * @return 语速级别
     */
    @PostMapping("/speechSpeed")
    public String speechSpeed() {
        String id = (String) session.getAttribute("id");
        if (id == null) return "请重新登录";

        this.speechSpeed = easyVoice.speechSpeed("E:\\voice.m4a").getJSONArray("results").getJSONObject(0).getInt("name");
        if (this.speechSpeed >= 4 && this.speechSpeed <= 6)
            return String.valueOf(this.speechSpeed + 4);
        else if (this.speechSpeed >= 7)
            return String.valueOf(this.speechSpeed - 6);
        else return String.valueOf(this.speechSpeed + 4);
    }

    /**
     * 监测教师当前的情感情绪
     *
     * @return 情感，共12大类
     */
    @PostMapping("/emotion")
    public String emotion() {
        String id = (String) session.getAttribute("id");
        if (id == null) return "请重新登录";

        JSONObject result = easyVoice.emotion("E:\\voice.m4a").getJSONArray("results").getJSONObject(0);
        this.emotion = result.getString("name");
        double score = result.getDouble("score");
        switch (emotion) {
            case "passionate":
                return String.format("%.2f", 90 * score);
            case "sorrow":
                return String.format("%.2f", 60 * score);
            case "peaceful":
                return String.format("%.2f", 80 * score);
            case "surprise":
                return String.format("%.2f", 70 * score);
            case "exciting":
                return String.format("%.2f", 95 * score);
            case "but":
                return String.format("%.2f", 55 * score);
            case "humorous":
                return String.format("%.2f", 110 * score);
            case "affectionate":
                return String.format("%.2f", 120 * score);
            case "angry":
                return String.format("%.2f", 40 * score);
            case "happy":
                return String.format("%.2f", 100 * score);
            case "proud":
                return String.format("%.2f", 105 * score);
            case "nervous":
                return String.format("%.2f", 50 * score);
            default:
                return String.valueOf(80);
        }
    }

    /**
     * 敏感词识别
     *
     * @return
     */
    @PostMapping("/sensitivity")
    public String sensitivity() {
        String id = (String) session.getAttribute("id");
        if (id == null) return "请重新登录";

        String word = words.getSpeech("D:\\Cache\\ShotCache\\voice.m4a");
        JSONObject result = baiDuAPI.auditTest(word);

        String answer = "";
        JSONArray review = result.getJSONArray("review");
        for (int i = 0; i < review.length(); i++) {
            JSONObject object = review.getJSONObject(i);
            JSONArray array = object.getJSONArray("hit");
            if (array.length() == 0) continue;

            answer += "教师语言中可能出现[" + Label.textAudit(object.getInt("label")) + "]词汇：\n";
            for (int j = 0; j < array.length(); j++) answer += array.getString(j) + " ";
            answer += "\n";
        }


        JSONArray reject = result.getJSONArray("reject");
        for (int i = 0; i < reject.length(); i++) {
            JSONObject object = reject.getJSONObject(i);
            JSONArray array = object.getJSONArray("hit");
            if (array.length() == 0) continue;

            answer += "教师语言中出现[" + Label.textAudit(object.getInt("label")) + "]词汇：\n";
            for (int j = 0; j < array.length(); j++) answer += array.getString(j) + " ";
            answer += "\n";
        }

        if (answer.equals("")) answer = "敏感词汇\n当前未出现敏感词汇";

        return answer;
    }

    /**
     * 肢体动作分析
     *
     * @return
     */
    @PostMapping("/bodyAnalysis")
    public String bodyAnalysis() {
        String id = (String) session.getAttribute("id");
        if (id == null) return "请重新登录";

        JSONObject result = aipBodyAnalysis.bodyAnalysis("E:\\pic.png", new HashMap<>());
        logger.info(result.toString());
        return result.toString();
    }

    @PostMapping("/start")
    public void start() throws FrameGrabber.Exception {
        String id = (String) session.getAttribute("id");
        if (id == null) return;

        ffmpegFrameGrabber = FFmpegFrameGrabber.createDefault("D:\\Cache\\ShotCache\\A303front.mp4");
        ffmpegFrameGrabber.start();
    }

    @PostMapping("/audioCut")
    public void audioCut() {
        Media.audioCut("D:\\Cache\\ShotCache\\A303.wav", "D:\\Cache\\ShotCache\\voice.wav", cutTime, cutTime + 6);
        cutTime += 6;
    }

    @PostMapping("/screenshot")
    public void shot() throws FrameGrabber.Exception {
        String id = (String) session.getAttribute("id");
        if (id == null) return;

        logger.info("shot");
        Frame frame = null;
        int i = 0;
        frame = ffmpegFrameGrabber.grabImage();
        for (i = 0; i < 1 * 24; i++) {
            frame = ffmpegFrameGrabber.grabImage();
        }

        if (frame == null || frame.image == null) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        String imageMat = "jpg";
        String fileName = "D:\\Cache\\ShotCache\\shot.jpg";
        BufferedImage bi = converter.getBufferedImage(frame);
        File output = new File(fileName);
        try {
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
