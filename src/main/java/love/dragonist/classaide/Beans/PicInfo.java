package love.dragonist.classaide.Beans;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/3/26
 * \* Time: 20:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class PicInfo {
    int num;
    int sleepnum;
    int phonenum;

    public PicInfo(int num, int sleepnum, int phonenum) {
        this.num = num;
        this.sleepnum = sleepnum;
        this.phonenum = phonenum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSleepnum() {
        return sleepnum;
    }

    public void setSleepnum(int sleepnum) {
        this.sleepnum = sleepnum;
    }

    public String getPhonenum() {
        return String.valueOf(phonenum);
    }

    public void setPhonenum(int phonenum) {
        this.phonenum = phonenum;
    }
}
