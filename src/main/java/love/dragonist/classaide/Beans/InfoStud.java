package love.dragonist.classaide.Beans;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/3/25
 * \* Time: 16:42
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class InfoStud implements Comparable<InfoStud>{
    private Location location;
    private String name;
    private String score;

    public InfoStud(Location location, String name, String score) {
        this.location = location;
        this.name = name;
        this.score = score;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "InfoStud{" +
                "location=" + location +
                ", name='" + name + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    @Override
    public int compareTo(InfoStud o) {
        if(this.getLocation().getTop()>o.getLocation().getTop())return -1;
        else if(this.getLocation().getTop()<o.getLocation().getTop())return 1;
        else return 0;
    }
}
