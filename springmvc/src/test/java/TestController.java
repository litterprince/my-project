import com.spring.simple.po.UserBean;
import com.spring.simple.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件，可以指定多个配置文件，locations指定的是一个数组
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml", "classpath:spring/spring-context.xml"})
//启动事务控制
@Transactional
@WebAppConfiguration
public class TestController {
    @Autowired
    UserService userService;

    @Test
    public void test(){
        UserBean user = new UserBean();
        user.setUserName("admin");
        user.setPassword("admin");
        List<UserBean> userList = userService.findByParam(user);
        Assert.assertNotNull(userList);
    }
}
