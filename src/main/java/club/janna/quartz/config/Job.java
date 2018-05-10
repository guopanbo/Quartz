package club.janna.quartz.config;

/**
 * Created by guopanbo on 18/5/10.
 */
public class Job {
    private String name;
    private String target;

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
