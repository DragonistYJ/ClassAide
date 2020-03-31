package love.dragonist.classaide.util;

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


public class BaiDuAPI extends BaseClient {
    private Logger logger = LoggerFactory.getLogger(BaiDuAPI.class);

    public BaiDuAPI(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 识别语言中的敏感词汇
     *
     * @param content 文本内容，不超过20000字节
     * @return
     */
    public JSONObject auditTest(String content) {
        AipRequest request = new AipRequest();

        preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE,
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_MSG);
        }

        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addBody("content", content);
        request.setUri("https://aip.baidubce.com/rest/2.0/antispam/v2/spam?access_token=" + accessToken);
        JSONObject object = requestServer(request).getJSONObject("result");

        logger.info(object.toString());
        return object;
    }

    /**
     * 学生上课状态识别
     *
     * @return
     */
    public JSONObject studentStatusDetect(String path) {
        String image = null;
        try {
            image = Base64Util.encode(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AipRequest request = new AipRequest();

        preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE,
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_MSG);
        }

        request.addHeader("Content-Type", "application/json");
        request.addBody("image", image);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        request.setUri("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/detection/yjhmm2?access_token=" + accessToken);
        JSONObject result = requestServer(request);
        logger.info(result.toString());
        return result;
    }

    /**
     * 统计教室里的学生人数
     *
     * @param path 图片路径
     * @return
     */
    public JSONObject studentNum(String path) {
        String image = null;
        try {
            image = Base64Util.encode(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AipRequest request = new AipRequest();

        preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE,
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_MSG);
        }

        request.addHeader("Content-Type", "application/json");
        request.addBody("image", image);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        request.setUri("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/detection/countpeople?access_token=" + accessToken);
        JSONObject result = requestServer(request);

        return result;
    }
}
