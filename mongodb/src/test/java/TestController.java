import com.spring.dao.impl.FileDao;
import com.spring.dao.impl.UserDao;
import com.spring.po.FileBean;
import com.spring.po.UserBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件，可以指定多个配置文件，locations指定的是一个数组
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml","classpath:spring/spring-context.xml"})
@WebAppConfiguration
public class TestController {
    @Autowired
    UserDao userDao;
    @Autowired
    FileDao fileDao;

    @Test
    public void user(){
        List<UserBean> userList = userDao.findList(0, 10);
        Assert.assertEquals(1, userList.size());
    }

    @Test
    public void file(){
        List<FileBean> fileList = fileDao.findList(0, 2);
        Assert.assertEquals(2, fileList.size());
    }
}
