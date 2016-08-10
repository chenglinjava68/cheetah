package org.cheetah.rest.provider;

import org.cheetah.common.logger.Err;
import org.cheetah.rest.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Max on 2016/7/20.
 */
@ControllerAdvice
public abstract class SpringMvcExceptionMapper<T> {
    private ExceptionMessageConverter<T> exceptionMessageConverter;

    public SpringMvcExceptionMapper(ExceptionMessageConverter<T> exceptionMessageConverter) {
        this.exceptionMessageConverter = exceptionMessageConverter;
    }

    @ExceptionHandler
    @ResponseBody
    public T exceptionHandler(Exception e) {
        Err.log(this.getClass(), "request an exception occurs", e);
        return exceptionMessageConverter.convert(e);
    }
}
