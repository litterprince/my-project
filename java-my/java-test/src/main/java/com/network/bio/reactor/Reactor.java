package com.network.bio.reactor;

import com.network.bio.reactor.handleImpl.AcceptEventHandler;

public class Reactor {
    private Selector selector = new Selector();
    private Dispatcher dispatcher = new Dispatcher(selector);
    private Acceptor acceptor;

    public static void main(String[] args){
        Reactor reactor = new Reactor(8080);
        reactor.start();
    }

    private Reactor(int port) {
        acceptor = new Acceptor(selector, port);
    }

    public void start() {
        dispatcher.registerEventHandler(EventType.ACCEPT, new AcceptEventHandler(selector));
        new Thread(acceptor, "Acceptor-" + acceptor.getPort()).start();
        dispatcher.handleEvents();
    }
}
