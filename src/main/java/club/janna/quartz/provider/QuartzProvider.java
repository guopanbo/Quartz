package club.janna.quartz.provider;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by guopanbo on 18/5/10.
 */
public class QuartzProvider {

    private SchedulerFactory factory;
    private Scheduler scheduler;

    private static class QuartzProviderFactory {
        private static QuartzProvider instance = new QuartzProvider();
    }

    public static QuartzProvider getInstance() {
        return QuartzProviderFactory.instance;
    }

    private QuartzProvider() {
        init();
    }

    private void init() {
        factory = new StdSchedulerFactory();
        try {
            scheduler = factory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
