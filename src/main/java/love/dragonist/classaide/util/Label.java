package love.dragonist.classaide.util;

public class Label {
    public static String textAudit(int label) {
        switch (label) {
            case 1:
                return "暴恐违禁";
            case 2:
                return "文本色情";
            case 3:
                return "政治敏感";
            case 4:
                return "恶意推广";
            case 5:
                return "低俗辱骂";
            default:
                return "低质灌水";
        }
    }
}
