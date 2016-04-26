package org.cheetah.configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by Max on 2016/4/26.
 */
public class ConfigurationFactory {
    private static ConfigurationFactory instance = getInstance();

    public ConfigurationFactory() {
    }

    private static ConfigurationFactory getInstance() {
        Iterator iterator = ServiceLoader.load(ConfigurationFactory.class).iterator();
        if(iterator.hasNext()) {
            return (ConfigurationFactory)iterator.next();
        } else {
            throw new IllegalStateException("ConfigurationFactory implement class not found!");
        }
    }

    public static ConfigurationFactory singleton() {
        return instance;
    }

    public WritableConfiguration fromDatabase(DataSource dataSource) {
        return instance.fromDatabase(dataSource);
    }

    public WritableConfiguration fromDatabase(DataSource dataSource, String tableName, String keyColumn, String valueColumn) {
        return instance.fromDatabase(dataSource, tableName, keyColumn, valueColumn);
    }

    public Configuration fromClasspath(String fileName) {
        return instance.fromClasspath(fileName);
    }

    public WritableConfiguration fromFileSystem(String fileName) {
        return instance.fromFileSystem(fileName);
    }

    public WritableConfiguration fromFileSystem(String dirPath, String fileName) {
        return instance.fromFileSystem(dirPath, fileName);
    }

    public WritableConfiguration fromFileSystem(File file) {
        return instance.fromFileSystem(file);
    }

    public Configuration fromUrl(String url) {
        return instance.fromUrl(url);
    }

    public Configuration fromUrl(URL url) {
        return instance.fromUrl(url);
    }

    public Configuration fromInputStream(InputStream in) {
        return instance.fromInputStream(in);
    }
}
