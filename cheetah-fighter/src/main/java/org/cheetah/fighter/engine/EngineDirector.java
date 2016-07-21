package org.cheetah.fighter.engine;

import org.cheetah.fighter.FighterConfig;

/**
 * 引擎主管-负责管理构建一个完整的引擎
 * Created by Max on 2016/2/19.
 */
public interface EngineDirector {

    Engine directEngine();

    void setFighterConfig(FighterConfig fighterConfig);

}
