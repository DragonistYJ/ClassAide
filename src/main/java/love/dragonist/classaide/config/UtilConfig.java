package love.dragonist.classaide.config;

import love.dragonist.classaide.EasyDL.recognition.Words;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean
    public Words words() {
        return new Words();
    }
}
