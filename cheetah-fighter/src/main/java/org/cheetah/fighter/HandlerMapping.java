package org.cheetah.fighter;

import org.cheetah.fighter.handler.Handler;

import java.util.List;
import java.util.Set;

/** 处理器映射器
 * Created by Max on 2016/2/23.
 */
public interface HandlerMapping extends Cloneable {

    List<Handler> getHandlers(EventBus.HandlerMapperKey mapperKey);

    void put(EventBus.HandlerMapperKey mapperKey,List<Handler> handlers);

    Set<EventBus.HandlerMapperKey> mapperKeys();

    boolean isExists(EventBus.HandlerMapperKey mapperKey);


}
