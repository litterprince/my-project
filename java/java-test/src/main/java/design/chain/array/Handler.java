package design.chain.array;

import design.chain.RequestSource;

public interface Handler {
    /**
     * Do handler.
     *
     * @param requestSource  数据源
     * @param handlerChain   传入当前的Chain进行类似递归式的调用。
     */
    void doHandler(RequestSource requestSource, HandlerChain handlerChain);
}
