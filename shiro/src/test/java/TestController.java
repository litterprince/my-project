import com.spring.dao.UserDao;
import com.spring.po.SysUserBean;
import org.apache.shiro.mgt.DefaultSecurityManager;
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
//加载配置文件，可以指定多个配置文件，locations指定的是一个数组
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml", "classpath:spring/spring-context.xml"})
//启动事务控制
@Transactional
@WebAppConfiguration
public class TestController {
    @Autowired
    UserDao userDao;
    @Autowired
    private DefaultSecurityManager securityManager;

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
        userDao.insertOne(user);
    }

    @Test
    public void findList(){
        SysUserBean user = new SysUserBean();
        user.setUserName("admin");
        List<SysUserBean> userList = userDao.findByParam(user);
        int i = userList.size();
    }
}
