package cheetah.core;

import cheetah.governor.Governor;

/**
 * Created by Max on 2016/2/21.
 */
class TeamContext {
    public static final ThreadLocal<Governor> governor = new ThreadLocal<>();

    public static void setGovernor(Governor governor) {
        TeamContext.governor.set(governor);
    }

    public static void getGovernor() {
        TeamContext.governor.get();
    }

    public static void removeGovernor() {
        TeamContext.governor.remove();
    }
}
