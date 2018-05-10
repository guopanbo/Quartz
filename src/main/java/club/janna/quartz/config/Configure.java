package club.janna.quartz.config;

import java.util.List;

/**
 * Created by guopanbo on 18/5/10.
 */
public class Configure {
    private List<SimpleGroup> simpleGroups;
    private List<CronGroup> cronGroups;

    public List<SimpleGroup> getSimpleGroups() {
        return simpleGroups;
    }

    public void setSimpleGroups(List<SimpleGroup> simpleGroups) {
        this.simpleGroups = simpleGroups;
    }

    public List<CronGroup> getCronGroups() {
        return cronGroups;
    }

    public void setCronGroups(List<CronGroup> cronGroups) {
        this.cronGroups = cronGroups;
    }
}
