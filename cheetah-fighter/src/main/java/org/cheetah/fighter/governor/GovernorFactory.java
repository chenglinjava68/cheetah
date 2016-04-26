package org.cheetah.fighter.governor;

/**
 * 事件管理者的生产工厂
 * Created by Max on 2016/2/21.
 */
public interface GovernorFactory {

    Governor createGovernor();
}
