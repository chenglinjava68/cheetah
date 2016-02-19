package cheetah.plugin;

import cheetah.distributor.event.Collector;
import cheetah.distributor.EventMessage;
import cheetah.distributor.handler.ApplicationEventHandler;
import cheetah.distributor.event.ApplicationEvent;
import cheetah.distributor.event.ApplicationListener;
import cheetah.distributor.event.Event;
import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * Created by Max on 2016/2/17.
 */
public class InterceptorChainTest {
    @Test
    public void chain() {
        InterceptorChain chain = new InterceptorChain();
        chain.addInterceptor(new InterceptorTest());
        ApplicationEventHandler handler = (ApplicationEventHandler) chain.pluginAll(new ApplicationEventHandler(
                (ApplicationListener) event ->
                        System.out.println("a")
                , Executors.newCachedThreadPool()));
        handler.handle(new EventMessage(new ApplicationEvent("abc") {
        }, Collector.STATE_CALL_BACK), true);
    }

    @Plugins({@Registry(type = ApplicationEventHandler.class, method = "statelessNativeAsyncHandle",
            args = {Event.class})})
    public static class InterceptorTest implements Interceptor {
        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            Object result = null;
            System.out.println("begin");
            result = invocation.proceed();
            Thread.sleep(1000);
            System.out.println("end");
            return result;
        }

        @Override
        public Object intercept(Object object) throws Throwable {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean supportType(Object target) {
            return target instanceof ApplicationEventHandler;
        }
    }

}
