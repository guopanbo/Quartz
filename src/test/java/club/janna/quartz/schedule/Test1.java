package club.janna.quartz.schedule;


import club.janna.quartz.job.TestJob;
import club.janna.quartz.job.TestJob2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by guopanbo on 18/5/10.
 */
public class Test1 {
    public static void main(String[] args) {
        SchedulerFactory factory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = factory.getScheduler();
            JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity("testJob1", "group1").build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger1", "group1").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
            JobDetail job2 = JobBuilder.newJob(TestJob2.class).build();
            Trigger trigger2 = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
            scheduler.scheduleJob(job2, trigger2);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
