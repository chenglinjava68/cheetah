package cheetah.util;

import java.util.UUID;

/**
 * Created by Max on 2016/1/11.
 */
public abstract class IDGenerator {

    public static String generateId() {
        String random =  (Math.random() + "").replace(".", "").substring(1, 6);
        return UUID.randomUUID().toString() + "-" + random;
    }

}
