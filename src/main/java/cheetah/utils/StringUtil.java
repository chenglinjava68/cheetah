package cheetah.utils;

import java.util.UUID;

/**
 * Created by Max on 2016/1/11.
 */
public class StringUtil {

    public static String uuid() {
        String random =  (Math.random() + "").replace(".", "").substring(1, 6);
        return UUID.randomUUID().toString() + "-" + random;
    }

}
