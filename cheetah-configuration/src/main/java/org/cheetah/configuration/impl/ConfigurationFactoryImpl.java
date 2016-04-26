package org.cheetah.configuration.impl;

import org.cheetah.configuration.Configuration;
import org.cheetah.configuration.ConfigurationException;
import org.cheetah.configuration.ConfigurationFactory;
import org.cheetah.configuration.WritableConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Max on 2016/4/26.
 */
public class ConfigurationFactoryImpl extends ConfigurationFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationFactoryImpl.class);

    public ConfigurationFactoryImpl() {
    }

    /*public WritableConfiguration fromDatabase(DataSource dataSource) {
        return new ConfigurationDbImpl(dataSource);
    }

    public WritableConfiguration fromDatabase(DataSource dataSource, String tableName, String keyColumn, String valueColumn) {
        return new ConfigurationDbImpl(dataSource, tableName, keyColumn, valueColumn);
    }*/

    public Configuration fromClasspath(String fileName) {
        InputStream in = this.getClass().getResourceAsStream(fileName);
        return new ConfigurationInputStreamImpl(in);
    }

    public WritableConfiguration fromFileSystem(String fileName) {
        return new ConfigurationFileImpl(fileName);
    }

    public WritableConfiguration fromFileSystem(String dirPath, String fileName) {
        return new ConfigurationFileImpl(dirPath, fileName);
    }

    public WritableConfiguration fromFileSystem(File file) {
        return ConfigurationFileImpl.fromFile(file);
    }

    public Configuration fromUrl(String url) {
        try {
            return this.fromUrl(new URL(url));
        } catch (MalformedURLException var3) {
            LOGGER.error("url is not correct!");
            throw new ConfigurationException("url is not correct!");
        }
    }

    public Configuration fromUrl(URL url) {
        if(url == null) {
            throw new ConfigurationException("url is null!");
        } else {
            try {
                return new ConfigurationInputStreamImpl(url.openStream());
            } catch (IOException var3) {
                LOGGER.error("read url failure!");
                throw new ConfigurationException("read url failure!");
            }
        }
    }

    public Configuration fromInputStream(InputStream in) {
        return new ConfigurationInputStreamImpl(in);
    }
}
