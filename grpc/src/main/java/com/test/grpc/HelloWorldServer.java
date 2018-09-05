package com.test.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.logging.Logger;

public class HelloWorldServer {
    private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

    /* The port on which the server should run */
    private int port = 50051;
    private Server server;

    private void start(){

    }
}
