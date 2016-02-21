package cheetah.distributor.machinery.actor;

import akka.actor.UntypedActor;

/**
 * Created by Max on 2016/2/19.
 */
public class ActorUnit extends UntypedActor {
    @Override
    public void preStart() {
        System.out.println("prestart");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("receive");
    }
}
