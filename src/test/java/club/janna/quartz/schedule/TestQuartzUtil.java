package club.janna.quartz.schedule;

import club.janna.quartz.job.TestJob;
import club.janna.quartz.util.QuartzUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guopanbo on 18/5/11.
 */
public class TestQuartzUtil {

    @Test
    public void testAdd() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", "testJob123");
        map.put("areaCode", 1);
        map.put("address", 2);
        QuartzUtil.addJob(TestJob.class, "testJob123", "testGroup123", 5, map);
    }
}
