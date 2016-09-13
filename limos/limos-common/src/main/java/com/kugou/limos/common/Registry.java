package com.kugou.limos.common;

import java.lang.annotation.*;

/**
 * Created by Max on 2016/8/20.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Registry {
    /**
     * 接口名
     * @return
     */
    String value();

    /**
     * 并发数
     * @return
     */
    int executes() default 0;

    /**
     * 吞吐率，与period相关，如果使用默认的period即控制1秒内流量进出
     * @return
     */
    int throughput() default 0;

    /**
     * 单位为秒
     * @return
     */
    int period() default 1;


}
