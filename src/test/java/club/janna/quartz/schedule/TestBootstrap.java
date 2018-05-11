package club.janna.quartz.schedule;

import org.junit.Test;

/**
 * Created by guopanbo on 18/5/10.
 */
public class TestBootstrap {

    @Test
    public void testInit() {
        new Bootstrap().init();
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
