package design.chain;

import design.chain.array.DefaultHandlerChain;
import design.chain.array.HeaderHandler;
import design.chain.link.LinkHandler;

public class Main {
    public static void main(String[] args) {
        testArray();

        testLink();
    }

    public static void testArray(){
        DefaultHandlerChain handlerChain = new DefaultHandlerChain();
        handlerChain.addHandler(new HeaderHandler("1"));
        handlerChain.addHandler(new HeaderHandler("2"));
        handlerChain.doChain(new RequestSource(10086));
    }

    public static void testLink(){
        LinkHandler handler1 = new LinkHandler(null, "handler1");
        LinkHandler handler2 = new LinkHandler(handler1, "handler2");
        LinkHandler handler3 = new LinkHandler(handler2, "handler3");
        handler3.doHandler(new RequestSource(10086));
    }
}
