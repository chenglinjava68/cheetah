package org.cheetah.rest.provider;

import org.cheetah.common.logger.Err;
import org.cheetah.rest.ApiResult;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Max
 */
@Provider
public class JarrsExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        if (e instanceof NotFoundException) {
            throw e;
        }

        Err.log(getClass(), "api error.", e);
        ApiResult result = ApiResultHelper.doGetApiResult(e);
        return Response.ok().entity(result).build();
    }
}
