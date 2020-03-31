package love.dragonist.classaide.util;

public class WeekNum {
    public static String num2Chinese(Integer week) {
        switch (week) {
            case 1: return "一";
            case 2: return "二";
            case 3: return "三";
            case 4: return "四";
            case 5: return "五";
            case 6: return "六";
            default: return "七";
        }
    }
}
