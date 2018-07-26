import com.spring.rabbitmq.Consumer;
import com.spring.rabbitmq.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件，可以指定多个配置文件，locations指定的是一个数组
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml","classpath:spring/spring-context.xml"})
@WebAppConfiguration
public class TestController {
    @Autowired
    private Producer producer;
    private String queueId = "test_mq";

    @Test
    public void testQueue() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", "hello rabbitmq");
            // 注意：第二个属性是 Queue 与 交换机绑定的路由
            producer.sendQueue(queueId + "_exchange", queueId + "_patt", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
