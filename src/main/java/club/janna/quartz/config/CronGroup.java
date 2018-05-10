package club.janna.quartz.config;

import java.util.List;

/**
 * Created by guopanbo on 18/5/10.
 */
public class CronGroup {
    private String name;
    private List<CronJob> jobs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CronJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<CronJob> jobs) {
        this.jobs = jobs;
    }
}
