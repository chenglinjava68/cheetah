package cheetah.plugin;

import cheetah.distributor.event.Event;
import cheetah.distributor.machine.Machine;

/**
 * Created by Max on 2016/2/17.
 */
public class InterceptorChainTest {


    @Plugins({@Registry(type = Machine.class, method = "statelessNativeAsyncHandle",
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
            return target instanceof Machine;
        }
    }

}
