package club.janna.quartz.schedule;

import club.janna.quartz.config.*;
import club.janna.quartz.util.QuartzUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;

/**
 * Created by guopanbo on 18/5/10.
 */
public class Bootstrap implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * 加载配置信息
     * @return
     */
    public Configure load() {
        try {
            Yaml yaml = new Yaml();
            String confPath = Thread.currentThread().getContextClassLoader().getResource("quartz.yml").getPath();
            Configure conf = yaml.loadAs(new FileInputStream(confPath), Configure.class);
            logger.debug("load quartz configure by classpath : {}", confPath);
            return conf;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
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
            logger.debug("Job create complete");
        } else {
            throw new RuntimeException("configure load failed!");
        }
    }

    public void run() {
        init();
    }
}
