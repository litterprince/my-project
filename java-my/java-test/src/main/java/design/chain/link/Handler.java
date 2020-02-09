package design.chain.link;

import design.chain.RequestSource;

public interface Handler {
    /**
     * 指针指向下一个处理节点.
     *
     * @return the next
     */
    Handler getNext();

    /**
     * 处理具体逻辑.
     *
     * @param requestSource the request source
     */
    void doHandler(RequestSource requestSource);
}
