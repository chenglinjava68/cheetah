package org.cheetah.fighter.engine;

import org.cheetah.fighter.api.FighterConfig;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.worker.WorkerAdapterFactory;

/**
 * 引擎部组件的构建者
 * Created by Max on 2016/2/19.
 */
public interface EngineBuilder {

    /**
     * 构建引擎所需的处理器工厂
     * @return
     */
    HandlerFactory buildHandlerFactory();

    /**
     * 构建引擎所需的worker适配器工厂
     * @return
     */
    WorkerAdapterFactory buildWorkerAdapterFactory();

    /**
     * 构建引擎所需的异步者池子的工厂
     * @param fighterConfig 配置管理器
     * @return
     */
    AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig);
}
