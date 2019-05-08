package com.example.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.example.akka.entity.Message;
import com.typesafe.config.ConfigFactory;

public class TestActor extends AbstractActor {
    public static Props props() {
        return Props.create(TestActor.class);
    }

    @Override
    public Receive createReceive() {
        System.out.println(ConfigFactory.defaultApplication().getString("remote.actor.name"));
        return receiveBuilder().matchAny(msg -> execExecutorStartEvent(msg)).build();
    }

    private void execExecutorStartEvent(Object msg) {
        if (msg instanceof Message) {
            System.out.println("receive=" + ((Message) msg).getData());
        }
    }
}
