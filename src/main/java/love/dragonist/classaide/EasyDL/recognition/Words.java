package love.dragonist.classaide.EasyDL.recognition;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.speech.AipSpeech;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;

public class Words {
    @Autowired
    private AipOcr aipOcr;
    @Autowired
    private AipNlp aipNlp;
    @Autowired
    private AipSpeech aipSpeech;
    @Autowired
    private HashMap<String, String> pptOptions;
    @Autowired
    private HashMap<String, Object> simOptions;
    private Logger logger = LoggerFactory.getLogger(Words.class);

    /**
     * 截取当前页PPT，识别其中的文字
     *
     * @return 识别出来的文字
     */
    public String getPPT() {
        try {
            Runtime.getRuntime().exec("cmd /k java -jar ScreenShot.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ppt = "shot.png";
        JSONObject result = aipOcr.basicGeneral(ppt, pptOptions);
        System.out.println(result);
        JSONArray wordsResult = result.getJSONArray("words_result");
        for (int i = 0; i < wordsResult.length(); i++) {
            JSONObject object = wordsResult.getJSONObject(i);
            ppt = ppt + object.getString("words");
        }
        logger.info(ppt);
        return ppt;
    }

    /**
     * 将语音识别成文字
     *
     * @param path 音频文件路径
     * @return 识别出来的文字
     */
    public String getSpeech(String path) {
        JSONObject asrRes = aipSpeech.asr(path, "m4a", 16000, null);
        JSONArray result = asrRes.getJSONArray("result");
        logger.info(result.getString(0));
        return result.getString(0);
    }

    /**
     * 获取讲课内容与PPT内容的相似度
     *
     * @param text1 文本一
     * @param text2 文本二
     * @return 比较之后得到的相似度
     */
    public double getSimilarity(String text1, String text2) {
        String tmp = "";
        int k = 0;
        while (tmp.getBytes().length < 509 && k < text1.length()) tmp = tmp + text1.charAt(k++);
        text1 = tmp;

        tmp = "";
        k = 0;
        while (tmp.getBytes().length < 509 && k < text2.length()) tmp = tmp + text2.charAt(k++);
        text2 = tmp;

        if (text1.length() > text2.length()) text1 = text1.substring(0, text2.length());
        if (text2.length() > text1.length()) text2 = text2.substring(0, text1.length());

        JSONObject result = aipNlp.simnet(text1, text2, simOptions);
        System.out.println(result);
        logger.info(String.valueOf(result.getDouble("score")));
        return result.getDouble("score");
    }
}
