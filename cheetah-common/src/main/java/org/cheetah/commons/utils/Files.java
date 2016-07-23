package org.cheetah.commons.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Max on 2016/5/11.
 */
public final class Files {
    public final static String separator = "/";

    public static File getFile(String path) throws IOException {
        File file = new File(path);
        valid(file);
        return file;
    }

    public static void valid(File file) throws IOException {
        File parentFile = file.getParentFile();

        if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory())
            throw new IOException("Destination \'" + parentFile + "\' directory cannot be created");
        else if (file.exists() && !file.canWrite())
            throw new IOException("Destination \'" + file + "\' exists but is read-only");
    }
}
