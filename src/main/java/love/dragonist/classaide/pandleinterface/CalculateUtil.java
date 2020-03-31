package love.dragonist.classaide.pandleinterface;


import love.dragonist.classaide.Beans.InfoStud;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/3/25
 * \* Time: 17:41
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class CalculateUtil {
    public static int CalculatePhone(List<InfoStud> InfoStudList) {
        int num = 0;
        for (InfoStud infoStud : InfoStudList) {
            if (infoStud.getName().equals("phone")) num++;
        }
        return num;
    }

    public static int CalulateSleep(List<InfoStud> InfoStudList) {
        int sleepnum = 0;
        for (InfoStud sleepstud : InfoStudList) {
            if (sleepstud.getName().equals("sleep")) sleepnum++;
        }
        return sleepnum;
    }

    public static int CalculatePeople(List<InfoStud> InfoStudPeop) {
        return InfoStudPeop.size();
    }
}
