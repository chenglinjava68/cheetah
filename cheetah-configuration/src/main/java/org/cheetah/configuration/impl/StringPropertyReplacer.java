package org.cheetah.configuration.impl;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Max on 2016/4/26.
 */
public class StringPropertyReplacer {
    public static final String NEWLINE = System.getProperty("line.separator", "\n");
    private static final String FILE_SEPARATOR;
    private static final String PATH_SEPARATOR;
    private static final String FILE_SEPARATOR_ALIAS = "/";
    private static final String PATH_SEPARATOR_ALIAS = ":";
    private static final int NORMAL = 0;
    private static final int SEEN_DOLLAR = 1;
    private static final int IN_BRACKET = 2;

    private StringPropertyReplacer() {
    }

    public static String replaceProperties(String value) {
        return replaceProperties(value, (Properties)null);
    }

    public static Properties replacePropertyValues(Properties props) {
        if(props == null) {
            return null;
        } else {
            Properties ret = new Properties();
            Iterator i$ = props.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry e = (Map.Entry)i$.next();
                String key = (String)e.getKey();
                String value = (String)e.getValue();
                ret.put(key, replaceProperties(value));
            }

            return ret;
        }
    }

    public static String replaceProperties(String string, Properties props) {
        char[] chars = string.toCharArray();
        StringBuffer buffer = new StringBuffer();
        boolean properties = false;
        byte state = 0;
        int start = 0;

        for(int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if(c == 36 && state != 2) {
                state = 1;
            } else if(c == 123 && state == 1) {
                buffer.append(string.substring(start, i - 1));
                state = 2;
                start = i - 1;
            } else if(state == 1) {
                state = 0;
            } else if(c == 125 && state == 2) {
                if(start + 2 == i) {
                    buffer.append("${}");
                } else {
                    String value = null;
                    String key = string.substring(start + 2, i);
                    if("/".equals(key)) {
                        value = FILE_SEPARATOR;
                    } else if(":".equals(key)) {
                        value = PATH_SEPARATOR;
                    } else {
                        if(props != null) {
                            value = props.getProperty(key);
                        } else {
                            value = System.getProperty(key);
                        }

                        if(value == null) {
                            int colon = key.indexOf(58);
                            if(colon > 0) {
                                String realKey = key.substring(0, colon);
                                if(props != null) {
                                    value = props.getProperty(realKey);
                                } else {
                                    value = System.getProperty(realKey);
                                }

                                if(value == null) {
                                    value = resolveCompositeKey(realKey, props);
                                    if(value == null) {
                                        value = key.substring(colon + 1);
                                    }
                                }
                            } else {
                                value = resolveCompositeKey(key, props);
                            }
                        }
                    }

                    if(value != null) {
                        properties = true;
                        buffer.append(value);
                    }
                }

                start = i + 1;
                state = 0;
            }
        }

        if(!properties) {
            return string;
        } else {
            if(start != chars.length) {
                buffer.append(string.substring(start, chars.length));
            }

            return buffer.toString();
        }
    }

    private static String resolveCompositeKey(String key, Properties props) {
        String value = null;
        int comma = key.indexOf(44);
        if(comma > -1) {
            String key2;
            if(comma > 0) {
                key2 = key.substring(0, comma);
                if(props != null) {
                    value = props.getProperty(key2);
                } else {
                    value = System.getProperty(key2);
                }
            }

            if(value == null && comma < key.length() - 1) {
                key2 = key.substring(comma + 1);
                if(props != null) {
                    value = props.getProperty(key2);
                } else {
                    value = System.getProperty(key2);
                }
            }
        }

        return value;
    }

    static {
        FILE_SEPARATOR = File.separator;
        PATH_SEPARATOR = File.pathSeparator;
    }
}
