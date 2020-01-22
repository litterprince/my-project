package design.chain.array;

import design.chain.RequestSource;
import lombok.Setter;

public class HeaderHandler implements Handler {
    @Setter
    private String name;

    public HeaderHandler(String name) {
        this.name = name;
    }

    @Override
    public void doHandler(RequestSource requestSource, HandlerChain handlerChain) {
        // 处理数据
        Integer header = requestSource.getHeader();
        System.out.println("name=" + this.name + "；header handler= " + header);
        //继续下一个 你可以根据条件来决定是否继续进行chain
        handlerChain.doChain(requestSource);
    }
}
