package club.janna.quartz.config;

import java.util.List;

/**
 * Created by guopanbo on 18/5/10.
 */
public class SimpleGroup {
    private String name;
    private List<SimpleJob> jobs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SimpleJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<SimpleJob> jobs) {
        this.jobs = jobs;
    }
}
