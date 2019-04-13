package com.network.io.reactor;

public class Server {
    private Selector selector = new Selector();
    private Dispatcher eventLoop = new Dispatcher(selector);
    private Acceptor acceptor;

    Server(int port) {
        acceptor = new Acceptor(selector, port);
    }

    public void start() {
        eventLoop.registerEventHandler(EventType.ACCEPT, new AcceptEventHandler(selector));
        new Thread(acceptor, "Acceptor-" + acceptor.getPort()).start();
        eventLoop.handleEvents();
    }
}
