package club.janna.quartz.config;

/**
 * Created by guopanbo on 18/5/10.
 */
public class SimpleJob {
    private String name;
    private String target;
    private Integer interval;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
