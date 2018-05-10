package club.janna.quartz.schedule;

import club.janna.quartz.config.*;
import club.janna.quartz.provider.QuartzProvider;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.Job;
import org.yaml.snakeyaml.Yaml;

/**
 * Created by guopanbo on 18/5/10.
 */
public class InitQuartzByConfigure {



    /**
     * 加载配置信息
     * @return
     */
    public Configure load() {
        Yaml yaml = new Yaml();
        Configure conf = yaml.loadAs(Thread.currentThread().getContextClassLoader().getResourceAsStream("quartz.yml"), Configure.class);
        return conf;
    }

    /**
     * 初始化 quartz
     */
    public void init() {
        Configure conf = load();
        if(conf != null) {
            if(conf.getSimpleGroups() != null)
                for(SimpleGroup sg : conf.getSimpleGroups())
                    if(sg != null && sg.getJobs() != null)
                        for(SimpleJob sj : sg.getJobs())
                            if(sj != null && StringUtils.isNotBlank(sj.getTarget()) && sj.getInterval() != null) {
                                try {
                                    QuartzProvider.getInstance().getScheduler().scheduleJob(buildJobDetail(sj.getTarget(), sj.getName(), sg.getName()), buildSimpleTrigger(sj.getInterval(), sj.getName(), sg.getName()));
                                } catch (SchedulerException e) {
                                    e.printStackTrace();
                                }
                            }
            //cron
            if(conf.getCronGroups() != null)
                for(CronGroup cg : conf.getCronGroups())
                    if(cg != null && cg.getJobs() != null)
                        for(CronJob cj : cg.getJobs())
                            if(cj != null && StringUtils.isNotBlank(cj.getTarget()) && StringUtils.isNotBlank(cj.getCronExpression())) {
                                try {
                                    QuartzProvider.getInstance().getScheduler().scheduleJob(buildJobDetail(cj.getTarget(), cj.getName(), cg.getName()), buildCronTrigger(cj.getCronExpression(), cj.getName(), cg.getName()));
                                } catch (SchedulerException e) {
                                    e.printStackTrace();
                                }
                            }

        } else {
            throw new RuntimeException("configure load failed!");
        }
    }

    /**
     * 创建job
     * @param target
     * @param name
     * @param group
     * @return
     */
    private JobDetail buildJobDetail(String target, String name, String group) {
        try {
            return JobBuilder.newJob((Class<? extends Job>) Class.forName(target)).withIdentity(name, group).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建简单触发器
     * @param interval
     * @param name
     * @param group
     * @return
     */
    private Trigger buildSimpleTrigger(int interval, String name, String group) {
        return TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
    }

    /**
     * 创建cron触发器
     * @param cronExpression
     * @param name
     * @param group
     * @return
     */
    private Trigger buildCronTrigger(String cronExpression, String name, String group) {
        return TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
    }
}
