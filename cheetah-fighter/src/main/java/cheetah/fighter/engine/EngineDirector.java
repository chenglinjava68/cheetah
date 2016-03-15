package cheetah.fighter.engine;

import cheetah.fighter.core.Configuration;

/**
 * 引擎主管-负责管理构建一个完整的引擎
 * Created by Max on 2016/2/19.
 */
public interface EngineDirector {

    Engine directEngine();

    void setConfiguration(Configuration configuration);

}
