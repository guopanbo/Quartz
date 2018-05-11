package club.janna.quartz.schedule;

import club.janna.quartz.config.*;
import club.janna.quartz.util.QuartzUtil;
import org.apache.commons.lang3.StringUtils;
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
                                QuartzUtil.addJob(sj.getTarget(), sj.getName(), sg.getName(), sj.getInterval());
                            }
            //cron
            if(conf.getCronGroups() != null)
                for(CronGroup cg : conf.getCronGroups())
                    if(cg != null && cg.getJobs() != null)
                        for(CronJob cj : cg.getJobs())
                            if(cj != null && StringUtils.isNotBlank(cj.getTarget()) && StringUtils.isNotBlank(cj.getCronExpression())) {
                                QuartzUtil.addJob(cj.getTarget(), cj.getName(), cg.getName(), cj.getCronExpression());
                            }

        } else {
            throw new RuntimeException("configure load failed!");
        }
    }
}
