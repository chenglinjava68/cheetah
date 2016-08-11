package org.cheetah.rest.provider;

import org.cheetah.commons.logger.Debug;
import org.cheetah.rest.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by maxhuang on 2016/8/9.
 */
public class ApiResutSpringMvcHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class
                .isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Debug.log(ApiResutSpringMvcHandler.class, "MyResponseBodyAdvice==>beforeBodyWrite:{},{}", returnType, body);
        if(body == null)
            return ApiResult.ok().build();
        return ApiResult.ok().result(body).build();
    }
}
