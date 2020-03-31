package love.dragonist.classaide.Beans;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/3/26
 * \* Time: 21:04
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class SumInfo {
    int number;
    String Base64;
    int phone;
    int sleep;
    List<XY> phoneXy;
    List<XY> sleepXy;

    public SumInfo(int number, String base64, int phone, int sleep, List<XY> phoneXy, List<XY> sleepXy) {
        this.number = number;
        Base64 = base64;
        this.phone = phone;
        this.sleep = sleep;
        this.phoneXy = phoneXy;
        this.sleepXy = sleepXy;
    }

    public SumInfo() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBase64() {
        return Base64;
    }

    public void setBase64(String base64) {
        Base64 = base64;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public List<XY> getPhoneXy() {
        return phoneXy;
    }

    public void setPhoneXy(List<XY> phoneXy) {
        this.phoneXy = phoneXy;
    }

    public List<XY> getSleepXy() {
        return sleepXy;
    }

    public void setSleepXy(List<XY> sleepXy) {
        this.sleepXy = sleepXy;
    }
}
