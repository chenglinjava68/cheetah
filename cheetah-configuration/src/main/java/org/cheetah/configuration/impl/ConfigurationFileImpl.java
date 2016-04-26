package org.cheetah.configuration.impl;

import org.cheetah.commons.logger.Debug;
import org.cheetah.commons.utils.StringUtils;
import org.cheetah.configuration.ConfigurationException;
import org.cheetah.configuration.WritableConfiguration;

import java.io.*;
import java.util.*;

/**
 * Created by Max on 2016/4/26.
 */
public class ConfigurationFileImpl extends AbstractConfiguration implements WritableConfiguration {
    private PropertiesFileUtils pfu;
    private File file;

    public static ConfigurationFileImpl fromFile(File file) {
        return new ConfigurationFileImpl(file);
    }

    public ConfigurationFileImpl(String pathname) {
        this(new File(pathname));
    }

    public ConfigurationFileImpl(String dirPath, String fileName) {
        this(new File(dirPath, fileName));
    }

    public ConfigurationFileImpl(File file) {
        this.pfu = new PropertiesFileUtils("utf-8");
        if(file == null) {
            throw new ConfigurationException("File " + file.getName() + " is null!");
        } else if(!file.exists()) {
            throw new ConfigurationException("File " + file.getName() + " not found!");
        } else if(!file.canRead()) {
            throw new ConfigurationException("File " + file.getName() + " is unreadable!");
        } else {
            this.file = file;
            this.load();
        }
    }

    public void load() {
        this.hTable = new Hashtable();
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            System.out.println(this.file.getName());
            in = new FileInputStream(this.file);
            props.load(in);
            this.hTable = this.pfu.rectifyProperties(props);
            Debug.log(this.getClass(), "Load configuration from {} at {}", new Object[]{this.file.getAbsolutePath(), new Date()});
        } catch (IOException var11) {
            throw new ConfigurationException("Cannot load config file: " + this.file, var11);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException var10) {
                    throw new ConfigurationException("Cannot close input stream.", var10);
                }
            }

        }

    }

    public void save() {
        BufferedWriter out = null;

        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file), "iso8859-1"));
            this.store(this.getProperties(), out, "Config file for " + this.file);
        } catch (Exception var10) {
            throw new ConfigurationException(var10);
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException var9) {
                    throw new ConfigurationException("Cannot close input stream.", var9);
                }
            }

        }

    }

    private void store(Properties props, BufferedWriter out, String comments) throws IOException {
        if(StringUtils.isNotEmpty(comments)) {
            out.append("#" + comments);
            out.newLine();
        }

        out.write("#" + (new Date()).toString());
        out.newLine();
        synchronized(this) {
            Iterator i$ = props.entrySet().iterator();

            while(true) {
                if(!i$.hasNext()) {
                    out.flush();
                    break;
                }

                Map.Entry each = (Map.Entry)i$.next();
                String key = this.convertString((String)each.getKey(), true);
                String value = this.convertString((String)each.getValue(), false);
                out.write(key + "=" + value);
                out.newLine();
            }
        }

        Debug.log(this.getClass(), "Save configuration to {} at {}", new Object[]{this.file.getAbsolutePath(), new Date()});
    }

    private String convertString(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if(bufLen < 0) {
            bufLen = 2147483647;
        }

        StringBuilder outBuffer = new StringBuilder(bufLen);

        for(int x = 0; x < len; ++x) {
            char aChar = theString.charAt(x);
            if(aChar > 61 && aChar < 127) {
                if(aChar == 92) {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                } else {
                    outBuffer.append(aChar);
                }
            } else {
                switch(aChar) {
                    case '\t':
                        outBuffer.append('\\');
                        outBuffer.append('t');
                        break;
                    case '\n':
                        outBuffer.append('\\');
                        outBuffer.append('n');
                        break;
                    case '\f':
                        outBuffer.append('\\');
                        outBuffer.append('f');
                        break;
                    case '\r':
                        outBuffer.append('\\');
                        outBuffer.append('r');
                        break;
                    case ' ':
                        if(x == 0 || escapeSpace) {
                            outBuffer.append('\\');
                        }

                        outBuffer.append(' ');
                        break;
                    case '!':
                    case '#':
                    case ':':
                    case '=':
                        outBuffer.append('\\');
                        outBuffer.append(aChar);
                        break;
                    default:
                        outBuffer.append(aChar);
                }
            }
        }

        return outBuffer.toString();
    }

    public String toString() {
        return this.getClass().getSimpleName() + "{" + this.file + "}";
    }
}
