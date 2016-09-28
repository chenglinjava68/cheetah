package com.kugou.limos.contracts;

/**
 * Invoker代理
 * Created by Max on 2016/9/13.
 */
public interface InvokerProxy<T> {
    /**
     * 获取Invoker, Rpc调用者
     * @return
     */
    Invoker<T> getInvoker();

    /**
     * 获取Invoker关联的Invocation
     * @return
     */
    Invocation getInvocation();

    /**
     * 获取配置
     * @return
     */
    ThriftConfig getConfig();

    /**
     *
     * @return
     * @throws Exception
     */
    String execute() throws Exception;
}
