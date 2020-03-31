package love.dragonist.classaide.EasyDL.client;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EasyVoice extends BaseClient {
    private String emotionURL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/sound_cls/emotion";
    private String speechSpeedURL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/sound_cls/speechSpeed";
    private Logger logger = LoggerFactory.getLogger(EasyVoice.class);

    public EasyVoice(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    public JSONObject emotion(String sound, int top_num) {
        return this.voiceDL(emotionURL, sound, top_num);
    }

    public JSONObject emotion(String sound) {
        return this.voiceDL(emotionURL, sound, 1);
    }

    public JSONObject speechSpeed(String sound, int top_num) {
        return this.voiceDL(speechSpeedURL, sound, top_num);
    }

    public JSONObject speechSpeed(String sound) {
        return this.voiceDL(speechSpeedURL, sound, 1);
    }

    /**
     * EasyDL平台语音分类
     * @param url 不同分类的地址
     * @param sound  音频地址
     * @param top_num  返回的主题数，默认是1
     * @return
     */
    private JSONObject voiceDL(String url, String sound, int top_num) {
        String base64Content = null;
        try {
            base64Content = Base64Util.encode(Files.readAllBytes(Paths.get(sound)));
        } catch (IOException e) {
            logger.error("sound file open failed");
        }
        AipRequest request = new AipRequest();
        this.preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE, "No permission to access data");
        } else {
            request.addBody("sound", base64Content);
            request.addBody("top_num", top_num);
            request.setUri(url + "?access_token=" + this.accessToken);
            request.setBodyFormat(EBodyFormat.RAW_JSON);
            request.addHeader("Content-Type", "application/json");
            JSONObject object = this.requestServer(request);
            logger.info(object.toString());
            return object;
        }
    }
}
