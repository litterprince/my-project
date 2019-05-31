package other.hessian.service2;

import com.caucho.hessian.client.HessianProxyFactory;
import other.hessian.service2.bean.User;

import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        String url = "http://localhost:8080";
        HessianProxyFactory factory = new HessianProxyFactory();
        HelloService service = (HelloService) factory.create(HelloService.class, url);
        User jeff = service.sayHello("jeff");
        System.out.println(jeff.getName());
    }
}
