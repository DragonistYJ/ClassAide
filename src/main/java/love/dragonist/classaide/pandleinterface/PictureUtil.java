package love.dragonist.classaide.pandleinterface;

import love.dragonist.classaide.Beans.InfoStud;
import love.dragonist.classaide.Beans.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/5/8
 * \* Time: 23:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class PictureUtil {
    //框出异常状态的学生
    public static void circleStudent(String src, String outPut, List<InfoStud> infoStuds) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(src));
            Graphics2D graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.setColor(Color.RED);
            graphics2D.setStroke(new BasicStroke(3));
            Location location = new Location();
            for (InfoStud e : infoStuds) {
                if (e.getName().equals("phone")) {
                    graphics2D.setColor(Color.RED);
                    draw(e.getLocation(), graphics2D);
                } else if (e.getName().equals("sleep")) {
                    graphics2D.setColor(Color.GREEN);
                    draw(e.getLocation(), graphics2D);
                } else {
                }
            }
            ImageIO.write(image, "JPG", new FileOutputStream(outPut));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void draw(Location location, Graphics2D graphics2D) {
        graphics2D.drawLine((int) location.getLeft(), (int) location.getTop(),
                (int) (location.getLeft() + location.getWidth()), (int) location.getTop());
        graphics2D.drawLine((int) (location.getLeft() + location.getWidth()), (int) location.getTop(),
                (int) (location.getLeft() + location.getWidth()), (int) (location.getTop() + location.getHeight()));
        graphics2D.drawLine((int) (location.getLeft() + location.getWidth()), (int) (location.getTop() + location.getHeight()),
                (int) location.getLeft(), (int) (location.getTop() + location.getHeight()));
        graphics2D.drawLine((int) location.getLeft(), (int) (location.getTop() + location.getHeight()),
                (int) location.getLeft(), (int) location.getTop());
    }
}
