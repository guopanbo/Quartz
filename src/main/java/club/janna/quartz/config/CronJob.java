package club.janna.quartz.config;

/**
 * Created by guopanbo on 18/5/10.
 */
public class CronJob {
    private String name;
    private String target;
    private String cronExpression;

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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
