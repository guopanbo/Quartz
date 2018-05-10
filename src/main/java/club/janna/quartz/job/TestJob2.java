package club.janna.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by guopanbo on 18/5/10.
 */
public class TestJob2 implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Test job 2 is invoke");
    }
}
