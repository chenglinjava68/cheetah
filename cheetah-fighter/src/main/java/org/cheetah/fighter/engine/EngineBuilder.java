package org.cheetah.fighter.engine;

import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.FighterConfig;
import org.cheetah.fighter.HandlerMapping;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.worker.WorkerFactory;

/**
 * 引擎的构建者
 * Created by Max on 2016/2/19.
 */
public interface EngineBuilder {

    /**
     * 构建引擎所需的处理器工厂
     * @return
     */
    HandlerFactory buildHandlerFactory();

    /**
     * 构建引擎所需的工人工厂
     * @return
     */
    WorkerFactory buildWorkerFactory();

    /**
     * 构建引擎所需的事件映射
     * @return
     */
    HandlerMapping buildMapping();

    /**
     * 构建引擎所需的异步者池子的工厂
     * @param fighterConfig 配置管理器
     * @return
     */
    AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig);
}
