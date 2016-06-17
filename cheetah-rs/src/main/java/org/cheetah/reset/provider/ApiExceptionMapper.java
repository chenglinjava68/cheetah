package org.cheetah.reset.provider;

import org.cheetah.commons.PlatformException;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.reset.ApiExceptionMetadata;
import org.cheetah.reset.ApiResult;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Max
 */
@Provider
public class ApiExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        if (e instanceof NotFoundException) {
            throw e;
        }

        Loggers.me().error(getClass(), "api error.", e);
        ApiResult result = doGetApiResult(e);
        return Response.ok().entity(result).build();
    }

    private ApiResult doGetApiResult(RuntimeException e) {

        ApiExceptionMetadata metadata = getExceptionMetadata(e);
        if (null != metadata) {
            return ApiResult.error(metadata.code()).message(metadata.message()).build();
        }

        if (e instanceof PlatformException) {
            PlatformException error = (PlatformException) e;
            return ApiResult.error(Integer.parseInt(error.getErrorCode())).message(error.message()).build();
        }

        return ApiResult.error().message("unknown error.").build();
    }

    private ApiExceptionMetadata getExceptionMetadata(RuntimeException ex) {
        return ex.getClass().getDeclaredAnnotation(ApiExceptionMetadata.class);
    }
}
