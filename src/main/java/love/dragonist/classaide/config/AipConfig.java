package love.dragonist.classaide.config;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.speech.AipSpeech;
import love.dragonist.classaide.EasyDL.client.EasyVoice;
import love.dragonist.classaide.util.BaiDuAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AipConfig {
    private static final String APP_ID = "16143982";
    private static final String API_KEY = "4jXxljxXKfQPMSTI553QipH3";
    private static final String SECRET_KEY = "MGtqkVxgSBr5DgVB83GvUCTLbrWS7656";

    @Bean
    public AipSpeech aipSpeech() {
        return new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
    }

    @Bean
    public AipOcr aipOcr() {
        return new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }

    @Bean
    public AipNlp aipNlp() {
        return new AipNlp(APP_ID, API_KEY, SECRET_KEY);
    }

    @Bean
    public EasyVoice easyTeacher() {
        return new EasyVoice(APP_ID, API_KEY, SECRET_KEY);
    }

    @Bean
    public BaiDuAPI baiDuAPI() {
        return new BaiDuAPI(APP_ID, API_KEY, SECRET_KEY);
    }

    @Bean
    public AipBodyAnalysis aipBodyAnalysis() {
        return new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);
    }
}
