package com.kugou.dog.common;

import java.lang.annotation.*;

/**
 * Created by Max on 2016/8/20.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Registry {
    String value() default "";

    int executes() default 0;

    int throughput() default 0;

    int period() default 1;


}
