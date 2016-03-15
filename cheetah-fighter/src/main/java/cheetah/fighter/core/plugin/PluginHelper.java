/**
 * Copyright 2009-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cheetah.fighter.core.plugin;

import cheetah.fighter.commons.utils.Assert;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Max
 */
final class PluginHelper implements MethodInterceptor {

    private Plugin plugin;
    private Map<Class<?>, Set<Method>> registryMap;

    private PluginHelper(Plugin plugin) {
        this.plugin = plugin;
        this.registryMap = getRegistryMap(plugin);
    }

    public static Object wrap(Object target, Plugin plugin, Structure structure) {
        Assert.notNull(structure);
        Enhancer enhancer = createEnhancer(target, plugin);
        // 创建代理对象
        return enhancer.create(structure.argumentTypes, structure.arguments);
    }

    public static Object wrap(Object target, Plugin plugin) {
        Enhancer enhancer = createEnhancer(target, plugin);
        // 创建代理对象
        return enhancer.create();
    }


    private static Enhancer createEnhancer(Object target, Plugin plugin) {
        Assert.notNull(target);
        Assert.notNull(plugin);
        Class<?> type = target.getClass();

        PluginHelper pluginHelper = new PluginHelper(plugin);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        // 回调方法
        enhancer.setCallback(pluginHelper);
        return enhancer;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        try {
            Set<Method> methods = registryMap.get(method.getDeclaringClass());
            if (methods != null && methods.contains(method)) {
                return plugin.intercept(new Invocation(o, methodProxy, args));
            }
            return methodProxy.invokeSuper(o, args);
        } catch (Exception e) {
            throw unwrapThrowable(e);
        }
    }

    private Map<Class<?>, Set<Method>> getRegistryMap(Plugin plugin) {
        Plugins pluginsAnnotation = plugin.getClass().getAnnotation(Plugins.class);
        // issue #251
        if (pluginsAnnotation == null) {
            throw new PluginException("No @Intercepts annotation was found in plugin " + plugin.getClass().getName());
        }
        Registry[] sigs = pluginsAnnotation.value();
        Map<Class<?>, Set<Method>> registryMap = new HashMap<>();
        for (Registry sig : sigs) {
            Set<Method> methods = registryMap.get(sig.type());
            if (methods == null) {
                methods = new HashSet<>();
                registryMap.put(sig.type(), methods);
            }
            try {
                Method method = sig.type().getMethod(sig.method(), sig.args());
                Class<?> methodObjClass = method.getDeclaringClass();
                if (methodObjClass != sig.type() && methodObjClass.isAssignableFrom(sig.type())) {
                    Set<Method> superClassMethods = registryMap.get(methodObjClass);
                    if (superClassMethods == null) {
                        superClassMethods = new HashSet<>();
                        registryMap.put(methodObjClass, superClassMethods);
                    }
                    superClassMethods.add(method);
                } else
                    methods.add(method);
            } catch (NoSuchMethodException e) {
                throw new PluginException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
            }
        }
        return registryMap;
    }

    public static Throwable unwrapThrowable(Throwable wrapped) {
        Throwable unwrapped = wrapped;
        while (true) {
            if (unwrapped instanceof InvocationTargetException) {
                unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
            } else if (unwrapped instanceof UndeclaredThrowableException) {
                unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
            } else {
                return unwrapped;
            }
        }
    }

    public static class Structure {
        private Class[] argumentTypes;
        private Object[] arguments;

        public Structure(Class[] argumentTypes, Object[] arguments) {
            Assert.notNull(argumentTypes);
            Assert.notNull(arguments);
            this.argumentTypes = argumentTypes;
            this.arguments = arguments;
        }

        public Class[] argumentTypes() {
            return argumentTypes;
        }

        public Object[] arguments() {
            return arguments;
        }
    }
}
