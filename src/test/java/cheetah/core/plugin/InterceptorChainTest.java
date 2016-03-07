package cheetah.core.plugin;

import cheetah.core.Interceptor;
import cheetah.core.event.Event;
import cheetah.worker.Command;

/**
 * Created by Max on 2016/2/17.
 */
public class InterceptorChainTest {

    public static class InterceptorTest implements Interceptor {

        @Override
        public boolean preHandle(Command command) throws Exception {
            return false;
        }

        @Override
        public void postHandle(Command command) throws Exception {

        }

        @Override
        public void afterCompletion(Command command, Exception var4) throws Exception {

        }

        @Override
        public boolean supportsType(Class<? extends Event> event) {
            return false;
        }
    }

}
