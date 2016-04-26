package org.cheetah.configuration.impl;

import org.cheetah.configuration.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by Max on 2016/4/26.
 */
public class ConfigurationInputStreamImpl extends AbstractConfiguration {
    private InputStream in;
    private PropertiesFileUtils pfu = new PropertiesFileUtils("utf-8");

    public ConfigurationInputStreamImpl(InputStream in) {
        this.in = in;
    }

    public void load() {
        this.hTable = new Hashtable();
        Properties props = new Properties();

        try {
            props.load(this.in);
            this.hTable = this.pfu.rectifyProperties(props);
        } catch (IOException var10) {
            throw new ConfigurationException("Cannot load config file", var10);
        } finally {
            if(this.in != null) {
                try {
                    this.in.close();
                } catch (IOException var9) {
                    throw new ConfigurationException("Cannot close input stream.", var9);
                }
            }

        }

    }
}
