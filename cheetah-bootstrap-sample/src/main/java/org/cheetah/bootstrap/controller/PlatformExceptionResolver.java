package org.cheetah.bootstrap.controller;


import org.cheetah.commons.logger.Loggers;
import org.cheetah.commons.utils.JsonSerializer;
import org.cheetah.rest.ApiResult;
import org.cheetah.rest.provider.DefaultExceptionMessageConverter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Max
 * @date 2015/5/7
 */
public class PlatformExceptionResolver extends SimpleMappingExceptionResolver {
    private DefaultExceptionMessageConverter exceptionMessageConverter = new DefaultExceptionMessageConverter();

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Loggers.logger().warn(this.getClass(), "Exception stack trace logs:");
        ex.printStackTrace();
        String viewName = determineViewName(ex, request);
        if (viewName == null) {
            return null;
        }
        if (ajaxRequest(request)) {
            try {
                if (ex == null) {
                    return null;
                }

                Loggers.logger().info(this.getClass(), ex.getMessage());
                Loggers.logger().warn(this.getClass(), ex.getMessage());


                ApiResult apiResult = exceptionMessageConverter.convert(ex);

                write(JsonSerializer.serialize(apiResult), response);

            } catch (Exception e) {
                Loggers.logger().warn(this.getClass(), "resolver exception occur a error.", e);
            }
            return null;
        }
        Integer statusCode = determineStatusCode(request, viewName);
        if (statusCode != null) {
            applyStatusCodeIfPossible(request, response, statusCode);
        }
        return getModelAndView(viewName, ex, request);
    }

    private boolean ajaxRequest(HttpServletRequest request) {
        return request.getHeader("accept").contains("application/json")
                || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"))
                || (request.getContentType() != null && request.getContentType().contains("application/json"))
                || (request.getContentType() != null && request.getContentType().contains("multipart/form-data"));
    }

    private void write(String json, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
