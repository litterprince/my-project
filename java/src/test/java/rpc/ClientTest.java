package rpc;

import com.network.rpc.netty.RPC;
import com.network.rpc.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//TODO: set context file
@ContextConfiguration(locations = {""})
public class ClientTest {
    @Test
    public void start() {
        HelloService service = (HelloService) RPC.call(HelloService.class);
        System.out.println(service.sayHello("jeff"));
    }
}
