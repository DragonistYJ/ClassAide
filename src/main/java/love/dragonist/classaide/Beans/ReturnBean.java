package love.dragonist.classaide.Beans;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: gofreelee
 * \* Date: 2019/3/25
 * \* Time: 15:52
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ReturnBean {
    private String log_id;
    private List<InfoStud> results;

    public ReturnBean(String log_id, List<InfoStud> results) {
        this.log_id = log_id;
        this.results = results;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public List<InfoStud> getResults() {
        return results;
    }

    public void setResults(List<InfoStud> results) {
        this.results = results;
    }
}
