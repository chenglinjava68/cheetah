package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;

/**
 * Created by Max on 2016/2/16.
 */
public class ExceptionCallbackWrap {
    private EventMessage eventMessage;
    private Class<?> exceptionObject;
    private String exceptionMessage;

    public ExceptionCallbackWrap(EventMessage eventMessage, Class<?> exceptionObject, String exceptionMessage) {
        this.eventMessage = eventMessage;
        this.exceptionObject = exceptionObject;
        this.exceptionMessage = exceptionMessage;
    }

    public EventMessage getEventMessage() {
        return eventMessage;
    }

    public Class<?> getExceptionObject() {
        return exceptionObject;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
