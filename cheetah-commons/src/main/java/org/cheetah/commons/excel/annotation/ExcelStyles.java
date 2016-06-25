package org.cheetah.commons.excel.annotation;

import org.cheetah.commons.excel.StyleHandler;

import java.lang.annotation.*;

/**
 * Created by Max on 2016/6/25.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelStyles {
    Class<? extends StyleHandler> handler();
}
