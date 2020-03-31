package love.dragonist.classaide.Beans;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/3/25
 * \* Time: 16:29
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class Location {
    private float height;
    private float left;
    private float top;
    private float width;

    public Location(float height, float left, float top, float width) {
        this.height = height;
        this.left = left;
        this.top = top;
        this.width = width;
    }
    public Location(){}

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Location{" +
                "height=" + height +
                ", left=" + left +
                ", top=" + top +
                ", width=" + width +
                '}';
    }
}
