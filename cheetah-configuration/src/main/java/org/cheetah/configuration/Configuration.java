package org.cheetah.configuration;

import java.util.Date;
import java.util.Properties;

/**
 * Created by Max on 2016/4/26.
 */
public interface Configuration {
    String getString(String var1, String var2);

    String getString(String var1);

    int getInt(String var1, int var2);

    int getInt(String var1);

    long getLong(String var1, long var2);

    long getLong(String var1);

    double getDouble(String var1, double var2);

    double getDouble(String var1);

    boolean getBoolean(String var1, boolean var2);

    boolean getBoolean(String var1);

    Date getDate(String var1, Date var2);

    Date getDate(String var1);

//    <T> T getObject(String var1, Class<T> var2, T var3);
//
//    <T> T getObject(String var1, Class<T> var2);

    void setString(String var1, String var2);

    void setInt(String var1, int var2);

    void setLong(String var1, long var2);

    void setDouble(String var1, double var2);

    void setBoolean(String var1, boolean var2);

    void setDate(String var1, Date var2);

//    void setObject(String var1, Object var2);

    Properties getProperties();

    void load();
}
