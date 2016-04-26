package org.cheetah.configuration.impl;

import org.cheetah.commons.utils.Assert;
import org.cheetah.commons.utils.StringUtils;
import org.cheetah.configuration.Configuration;
import org.cheetah.configuration.ConfigurationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by Max on 2016/4/26.
 */
public abstract class AbstractConfiguration implements Configuration {
    private String dateFormat = "yyyy-MM-dd";
    private String prefix = "";
    private static final String DATE_FORMAT_KEY = "DATE_FORMAT";
    protected Hashtable<String, String> hTable;
    private PropertiesFileUtils pfu = new PropertiesFileUtils("utf-8");
//    private ObjectSerializer serializer = (new GsonSerializerBuilder()).build();

    public AbstractConfiguration() {
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void usePrefix(String prefix) {
        if(StringUtils.isNotBlank(prefix)) {
            this.prefix = prefix.endsWith(".")?prefix:prefix + ".";
        }

    }

    public String getString(String key, String defaultValue) {
        Assert.notBlank(key, "Key is null or empty!");
        String result = (String)this.getHashtable().get(key);
        if(result == null) {
            result = (String)this.getHashtable().get(this.prefix + key);
        }

        return result == null?defaultValue:result;
    }

    public String getString(String key) {
        return this.getString(key, "");
    }

    public void setString(String key, String value) {
        Assert.notBlank(key, "Key is null or empty!");
        if(StringUtils.isBlank(value)) {
            this.getHashtable().remove(key);
        } else {
            this.getHashtable().put(key, StringPropertyReplacer.replaceProperties(value));
        }
    }

    public int getInt(String key, int defaultValue) {
        String result = this.getString(key, String.valueOf(defaultValue));
        return StringUtils.isBlank(result)?defaultValue:Integer.parseInt(result);
    }

    public int getInt(String key) {
        return this.getInt(key, 0);
    }

    public void setInt(String key, int value) {
        this.setString(key, String.valueOf(value));
    }

    public long getLong(String key, long defaultValue) {
        String result = this.getString(key, String.valueOf(defaultValue));
        return StringUtils.isBlank(result)?defaultValue:Long.parseLong(result);
    }

    public long getLong(String key) {
        return this.getLong(key, 0L);
    }

    public void setLong(String key, long value) {
        this.setString(key, String.valueOf(value));
    }

    public double getDouble(String key, double defaultValue) {
        String result = this.getString(key, String.valueOf(defaultValue));
        return StringUtils.isBlank(result)?defaultValue:Double.parseDouble(result);
    }

    public double getDouble(String key) {
        return this.getDouble(key, 0.0D);
    }

    public void setDouble(String key, double value) {
        this.setString(key, String.valueOf(value));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String result = this.getString(key, String.valueOf(defaultValue));
        return StringUtils.isBlank(result)?defaultValue:Boolean.parseBoolean(result);
    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        this.setString(key, String.valueOf(value));
    }

    public Date getDate(String key, Date defaultValue) {
        String result = this.getString(key);

        try {
            return StringUtils.isBlank(result)?defaultValue:(new SimpleDateFormat(this.dateFormat)).parse(result);
        } catch (ParseException var5) {
            throw new ConfigurationException("日期解析错误！日期格式是：" + this.dateFormat + ", 日期：" + result, var5);
        }
    }

    public Date getDate(String key) {
        return this.getDate(key, (Date)null);
    }

    public void setDate(String key, Date value) {
        if(value == null) {
            this.setString(key, "");
        }

        this.setString(key, (new SimpleDateFormat(this.dateFormat)).format(value));
    }

   /* public <T> T getObject(String key, Class<T> objectClass, T defaultValue) {
        Object result = this.serializer.deserialize(this.getString(key), objectClass);
        return result == null?defaultValue:result;
    }

    public <T> T getObject(String key, Class<T> objectClass) {
        return this.getObject(key, objectClass, (Object)null);
    }

    public void setObject(String key, Object value) {
        this.setString(key, this.serializer.serialize(value));
    }*/

    public Properties getProperties() {
        return this.pfu.unRectifyProperties(this.getHashtable());
    }

    @Override
    public void load() {

    }

    private Hashtable<String, String> getHashtable() {
        if(this.hTable == null) {
            this.load();
        }

        return this.hTable;
    }
}
