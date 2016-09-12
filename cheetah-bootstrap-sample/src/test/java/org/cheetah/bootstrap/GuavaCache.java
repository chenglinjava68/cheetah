package org.cheetah.bootstrap;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/8/17.
 */
public class GuavaCache {
    private static Map<String, Integer> maps = Maps.newConcurrentMap();
    static {
        maps.putIfAbsent("a", 1);
        maps.putIfAbsent("b", 2);
        maps.putIfAbsent("c", 3);
        maps.putIfAbsent("d", 4);
    }
    private static LoadingCache<String, Integer> caches = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build(
            new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String s) throws Exception {
                    return maps.get(s);
                }
            }
    );

    @Test
    public void test() {
        caches.getUnchecked("a0");
    }
}
