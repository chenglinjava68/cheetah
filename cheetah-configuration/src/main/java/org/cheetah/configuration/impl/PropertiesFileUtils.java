package org.cheetah.configuration.impl;

import org.cheetah.configuration.ConfigurationException;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Max on 2016/4/26.
 */
public class PropertiesFileUtils {
    public static final String ISO_8859_1 = "iso8859-1";
    private String encoding = "UTF-8";

    public PropertiesFileUtils(String encoding) {
        this.encoding = encoding;
    }

    public Hashtable<String, String> rectifyProperties(Properties p) {
        if(p == null) {
            return null;
        } else {
            Hashtable ret = new Hashtable();
            Iterator i$ = p.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry e = (Map.Entry)i$.next();
                String key = (String)e.getKey();
                String value = (String)e.getValue();
                ret.put(key, StringPropertyReplacer.replaceProperties(this.rectifyStr(value)));
            }

            return ret;
        }
    }

    public Properties unRectifyProperties(Hashtable<String, String> h) {
        if(h == null) {
            return null;
        } else {
            Properties ret = new Properties();
            Iterator i$ = h.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry e = (Map.Entry)i$.next();
                String key = (String)e.getKey();
                String value = (String)e.getValue();
                ret.put(key, this.unRectifyStr(value));
            }

            return ret;
        }
    }

    public String rectifyStr(String raw) {
        if(raw == null) {
            return raw;
        } else {
            try {
                byte[] e = raw.getBytes("iso8859-1");
                String ret = new String(e, this.encoding);
                return ret;
            } catch (UnsupportedEncodingException var4) {
                throw new ConfigurationException("Unsupport Encoding:" + this.encoding, var4);
            }
        }
    }

    public String unRectifyStr(String validStr) {
        if(validStr == null) {
            return validStr;
        } else {
            try {
                byte[] e = validStr.getBytes(this.encoding);
                String ret = new String(e, "iso8859-1");
                return ret;
            } catch (UnsupportedEncodingException var4) {
                throw new ConfigurationException("Unsupport Encoding:" + this.encoding, var4);
            }
        }
    }
}
