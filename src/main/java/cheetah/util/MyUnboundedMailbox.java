package cheetah.util;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Envelope;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import com.typesafe.config.Config;
import scala.Option;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Max on 2016/2/23.
 */
public class MyUnboundedMailbox implements MailboxType {
    // This constructor signature must exist, it will be called by Akka
    public MyUnboundedMailbox(ActorSystem.Settings settings, Config config) {
// put your initialization code here
    }

    // The create method is called to create the MessageQueue
    public MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue() {
            private final Queue<Envelope> queue = new ConcurrentLinkedQueue<Envelope>();

            // these must be implemented; queue used as example
            public void enqueue(ActorRef receiver, Envelope handle) {
                queue.offer(handle);
            }

            public Envelope dequeue() {
                return queue.poll();
            }

            public int numberOfMessages() {
                return queue.size();
            }

            public boolean hasMessages() {
                return !queue.isEmpty();
            }

            public void cleanUp(ActorRef owner, MessageQueue deadLetters) {
                for (Envelope handle : queue) {
                    deadLetters.enqueue(owner, handle);
                }
            }
        };
    }
}
