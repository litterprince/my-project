package design.chain.array;

import design.chain.RequestSource;

import java.util.ArrayList;
import java.util.List;

public class DefaultHandlerChain implements HandlerChain {
    // 当前handler指针的位置
    private int pos = 0;
    private List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    @Override
    public void doChain(RequestSource requestSource) {
        int size = handlers.size();
        if (pos < size) {
            //注意对pos的处理
            Handler handler = handlers.get(pos++);
            // 好处是这里可以决定是否继续
            handler.doHandler(requestSource, this);
        }
    }
}
