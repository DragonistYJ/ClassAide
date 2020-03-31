package love.dragonist.classaide.pandleinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/5/5
 * \* Time: 15:09
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ConfigUtil {
    public static Object getParamFromProp(String key) {
        InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream("filepath.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Properties props = new Properties();
        try {
            props.load(br);
            return props.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

