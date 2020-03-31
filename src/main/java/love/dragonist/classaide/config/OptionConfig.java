package love.dragonist.classaide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class OptionConfig {
    @Bean
    public HashMap pptOptions() {
        HashMap<String, String> option = new HashMap<>();
        option.put("language_type", "CHN_ENG");
        option.put("detect_direction", "true");
        option.put("detect_language", "true");
        return option;
    }

    @Bean
    public HashMap simOptions() {
        HashMap<String, String> option = new HashMap<>();
        option.put("model", "GRNN");
        return option;
    }
}
