package club.janna.quartz.util;

import club.janna.quartz.job.JobStatus;
import club.janna.quartz.provider.QuartzProvider;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guopanbo on 18/5/11.
 */
public class QuartzUtil {

    /**
     * 添加job
     * @param jobClass
     * @param jobName
     * @param groupName
     * @param interval
     */
    public static void addJob(Class<Job> jobClass, String jobName, String groupName, int interval) {
        try {
            QuartzProvider.getInstance().getScheduler().scheduleJob(buildJobDetail(jobClass, jobName, groupName), buildSimpleTrigger(interval, jobName, groupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加job
     * @param target
     * @param jobName
     * @param groupName
     * @param interval
     */
    public static void addJob(String target, String jobName, String groupName, int interval) {
        try {
            QuartzProvider.getInstance().getScheduler().scheduleJob(buildJobDetail(target, jobName, groupName), buildSimpleTrigger(interval, jobName, groupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加job
     * @param target
     * @param jobName
     * @param groupName
     * @param cronExpression
     */
    public static void addJob(String target, String jobName, String groupName, String cronExpression) {
        try {
            QuartzProvider.getInstance().getScheduler().scheduleJob(buildJobDetail(target, jobName, groupName), buildCronTrigger(cronExpression, jobName, groupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加job
     * @param jobClass
     * @param jobName
     * @param groupName
     * @param cronExpression
     */
    public static void addJob(Class<Job> jobClass, String jobName, String groupName, String cronExpression) {
        try {
            QuartzProvider.getInstance().getScheduler().scheduleJob(buildJobDetail(jobClass, jobName, groupName), buildCronTrigger(cronExpression, jobName, groupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务
     * @param jobName
     * @param groupName
     */
    public static void deleteJob(String jobName, String groupName) {
        Scheduler scheduler = QuartzProvider.getInstance().getScheduler();
        TriggerKey tk = TriggerKey.triggerKey(jobName, groupName);
        JobKey jk = JobKey.jobKey(jobName, groupName);
        try {
            scheduler.pauseTrigger(tk);
            scheduler.unscheduleJob(tk);
            scheduler.deleteJob(jk);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有任务的状态
     * @return
     */
    public static List<JobStatus> getAllJobStatus() {
        Scheduler scheduler = QuartzProvider.getInstance().getScheduler();
        List<JobStatus> rs = null;
        try {
            for(String groupName : scheduler.getJobGroupNames()) {
                for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    if(rs == null)
                        rs = new ArrayList<JobStatus>();
                    JobStatus js = new JobStatus();
                    JobDetail jd = scheduler.getJobDetail(jobKey);
                    List<? extends Trigger> ts = scheduler.getTriggersOfJob(jobKey);

                    js.setName(jobKey.getName());
                    js.setGroupName(jobKey.getGroup());
                    js.setPrevFireTime(ts.get(0).getPreviousFireTime());
                    js.setNextFireTime(ts.get(0).getNextFireTime());
                    js.setJobClass(jd.getJobClass().toString());

                    rs.add(js);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 创建job
     * @param target
     * @param name
     * @param group
     * @return
     */
    public static JobDetail buildJobDetail(String target, String name, String group) {
        try {
            return buildJobDetail((Class<Job>) Class.forName(target), name, group);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建job
     * @param jobClass
     * @param name
     * @param group
     * @return
     */
    public static JobDetail buildJobDetail(Class<Job> jobClass, String name, String group) {
        return JobBuilder.newJob(jobClass).withIdentity(name, group).build();
    }

    /**
     * 创建简单触发器
     * @param interval
     * @param name
     * @param group
     * @return
     */
    public static Trigger buildSimpleTrigger(int interval, String name, String group) {
        return TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
    }

    /**
     * 创建cron触发器
     * @param cronExpression
     * @param name
     * @param group
     * @return
     */
    public static Trigger buildCronTrigger(String cronExpression, String name, String group) {
        return TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
    }
}
