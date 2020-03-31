package love.dragonist.classaide.Beans;

import java.util.Comparator;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: lee
 * \* Date: 2019/3/26
 * \* Time: 17:32
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class SortByLeft implements Comparator<InfoStud> {
    @Override
    public int compare(InfoStud o1, InfoStud o2) {
        return (int) (o1.getLocation().getLeft()-o2.getLocation().getLeft());
    }
}
