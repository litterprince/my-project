package design.chain.array;

import design.chain.RequestSource;

public interface HandlerChain {
    /**
     * 调用handler 处理 source.
     *
     * @param requestSource the request source
     */
    void doChain(RequestSource requestSource);
}
