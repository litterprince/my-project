package com.example.akka;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.example.akka.actor.TestActor;
import com.example.akka.entity.Message;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AkkaApplicationTests {
    private static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void tearDown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testActor() {
        new TestKit(system) {
            {
                ActorRef actorRef = system.actorOf(TestActor.props(), "test-actor");
                Message msg = new Message();
                msg.setId(1);
                msg.setData("hello");
                actorRef.tell(msg, Actor.noSender());
            }
        };
    }

}
