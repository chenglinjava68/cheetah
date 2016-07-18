package org.cheetah.fighter.async.akka;

import akka.actor.*;
import akka.pattern.Patterns;
import akka.routing.SmallestMailboxPool;
import com.typesafe.config.ConfigFactory;
import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.worker.AkkaWorker;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Actor工厂
 * Created by Max on 2016/2/29.
 */
public class ActorFactory implements AsynchronousFactory<ActorRef> {
    public final static String STATUS_CHECK_MSG = "STATUS_CHECK_MSG";  //actor创建完毕后消息验证内容
    public final static String STATUS_OK = "OK"; //消息验证的回应内容
    private ActorSystem actorSystem;
    private int actorSize = 1000;  //每次创建的actor个数

    public ActorFactory() {

    }

    @Override
    public ActorRef createAsynchronous(String name,List<Handler> handlers,
                                       List<Interceptor> interceptors) {
        SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create("1 minute"), Collections.<Class<? extends Throwable>>singletonList(Exception.class));
        ActorRef actor = actorSystem.actorOf(Props.create(AkkaWorker.class, handlers)
                .withRouter(new SmallestMailboxPool(actorSize).withSupervisorStrategy(strategy)), name);
        Info.log(this.getClass(), "create actor for name [" + name + "]");
        ok(actor);
        return actor;
    }

    private void ok(ActorRef worker) {
        Future<Object> ok = Patterns.ask(worker, STATUS_CHECK_MSG, 5000);
        try {
            Object result = Await.result(ok, Duration.create(5000, TimeUnit.MILLISECONDS));
            if(result.equals(STATUS_OK))
                return;
            else ok(worker);
        } catch (Exception e) {
            ok(worker);
        }
    }

    public void setActorSize(int actorSize) {
        this.actorSize = actorSize;
    }

    @Override
    public void start() {
        if (actorSystem == null || actorSystem.isTerminated())
            actorSystem = ActorSystem.create("cheetah_system", ConfigFactory.load("akka.conf"));
    }

    @Override
    public void stop() {
        while (!actorSystem.isTerminated()) {
            actorSystem.shutdown();
        }
        this.actorSystem = null;
    }

    public ActorSystem actorSystem() {
        return actorSystem;
    }

    public int actorSize() {
        return actorSize;
    }

}
