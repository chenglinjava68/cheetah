package org.cheetah.bootstrap;

import org.cheetah.bootstraps.jetty.JettyBootstrap;

/**
 * Created by maxhuang on 2016/8/5.
 */
public class Main {
    public static void main(String[] args) {
        JettyBootstrap bootstrap = new JettyBootstrap();
//        bootstrap.setWebMode(true);
        bootstrap.bootstrap();
    }
}
