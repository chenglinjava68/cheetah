package cheetah.event;

/**
 * Created by Max on 2016/1/10.
 */
public final class DomainEventEmitter {
    private DomainEventEmitter() {}

    public static <E extends DomainEvent> void launchedEvent(E domainEvent) {

    }


}
