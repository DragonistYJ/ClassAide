package love.dragonist.classaide.pandleinterface;

import com.google.gson.Gson;
import love.dragonist.classaide.Beans.InfoStud;
import love.dragonist.classaide.Beans.ReturnBean;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/3/25
 * \* Time: 18:25
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class JSONUtil {
    public static List<InfoStud> getStudList(String gsonString){
        Gson gson=new Gson();
        Object res=gson.fromJson(gsonString, ReturnBean.class);
        return ((ReturnBean) res).getResults();
    }
}
