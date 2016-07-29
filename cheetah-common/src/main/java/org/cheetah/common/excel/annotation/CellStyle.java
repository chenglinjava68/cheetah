package org.cheetah.common.excel.annotation;

import java.lang.annotation.*;

/**
 * Created by Max on 2016/6/25.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellStyle {
    Class<? extends org.cheetah.common.excel.StyleHandler> handler();
}
