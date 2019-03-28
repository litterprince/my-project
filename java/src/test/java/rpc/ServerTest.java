package rpc;

import com.network.rpc.netty.RPCServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//TODO: set context file
@ContextConfiguration(locations = {""})
public class ServerTest {
    @Test
    public void start(){
        RPCServer.start();
    }
}
