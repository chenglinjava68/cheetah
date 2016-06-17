package org.cheetah.rs.filter;


import org.cheetah.rs.ApiResult;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;

/**
 * @author Max
 */
@Provider
@Priority(99)
public class ApiResultFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        // skip error status or octet stream result.
        if (!isOKResponse(responseContext) || isStreamingResponse(responseContext)) {
            return;
        }

        // void or null result.
        Object entity = responseContext.getEntity();
        if (null == entity) {
            entity = Collections.emptyMap();
        }

        if (isNotApiResult(entity)) {
            entity = ApiResult.ok().result(entity).build();
        }

        responseContext.setStatusInfo(Response.Status.OK);
        responseContext.setEntity(entity, new Annotation[0], MediaType.APPLICATION_JSON_TYPE);
    }

    private boolean isOKResponse(ContainerResponseContext responseContext) {
        int status = responseContext.getStatus();
        return (status / 100) == 2;
    }

    private boolean isStreamingResponse(ContainerResponseContext responseContext) {
        return MediaType.APPLICATION_OCTET_STREAM_TYPE.isCompatible(responseContext.getMediaType());
    }

    private boolean isNotApiResult(Object entity) {
        return !(entity instanceof ApiResult);
    }
}
