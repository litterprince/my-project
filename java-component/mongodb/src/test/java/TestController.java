import com.spring.dao.FileMongoDao;
import com.spring.dao.UserMongoDao;
import com.spring.po.FileBean;
import com.spring.po.UserBean;
import org.bson.types.Binary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件，可以指定多个配置文件，locations指定的是一个数组
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml","classpath:spring/spring-context.xml"})
@WebAppConfiguration
public class TestController {
    @Autowired
    UserMongoDao userDao;
    @Autowired
    FileMongoDao fileDao;

    @Test
    public void user(){
        List<UserBean> userList = userDao.findList(0, 10);
        Assert.assertEquals(1, userList.size());
    }

    @Test
    public void insertUser(){
        UserBean user = new UserBean();
        user.setUserName("admin");
        user.setPassword("123456");
        userDao.store(user);
    }

    @Test
    public void file(){
        List<FileBean> fileList = fileDao.findList(0, 2);
        FileBean file = fileList.get(0);
        byte[] b = file.getImagecontent().getData();
        writeFile(b);
    }

    @Test
    public void insertFile(){
        FileBean file = new FileBean();
        byte[] b = readFile("D:/1.pdf");
        file.setImagecontent(new Binary(b));
        fileDao.store(file);
    }

    private byte[] readFile(String fileName) {
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return filecontent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeFile(byte[] contentInBytes){

        File file = new File("D:/2.pdf");

        try (FileOutputStream fop = new FileOutputStream(file)) {

            if (!file.exists()) {
                file.createNewFile();
            }

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
