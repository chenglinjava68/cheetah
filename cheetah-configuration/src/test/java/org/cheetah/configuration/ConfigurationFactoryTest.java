package org.cheetah.configuration;

import org.junit.Test;

import java.io.InputStream;

/**
 * Created by Max on 2016/4/26.
 */
public class ConfigurationFactoryTest {
    @Test
    public void test() {
        InputStream in = this.getClass().getResourceAsStream("application.properties");
        Configuration  configuration = ConfigurationFactory.singleton().fromClasspath("classpath://application.properties");
        System.out.println(configuration.getString("transport.socket.access.endpoint"));
    }
}
