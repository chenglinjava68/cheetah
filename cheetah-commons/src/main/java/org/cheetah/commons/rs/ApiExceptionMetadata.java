package org.cheetah.commons.rs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Max
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExceptionMetadata {

    /**
     * @return
     */
    int code();

    /**
     * @return
     */
    String message() default "Api Error.";
}
