import com.spring.simple.po.SysUserBean;
import com.spring.simple.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/spring-mvc.xml","classpath*:spring/spring-context.xml"})
@Transactional
@WebAppConfiguration
public class TestController {
    @Autowired
    UserService userService;

    @Test
    public void insertOne(){
        SysUserBean user = new SysUserBean();
        user.setId(UUID.randomUUID().toString());
        user.setUserName("admin");
        user.setUserCode("admin");
        user.setPassword("123456");
        user.setSalt("salt");
        user.setLocked("0");
        user.setCreateTime(new Date());
        userService.insertOne(user);
    }

    @Test
    public void findList(){
        SysUserBean user = new SysUserBean();
        user.setUserName("admin");
        List<SysUserBean> userList = userService.findByParam(user);
        Assert.assertEquals(2,userList.size());
    }
}
