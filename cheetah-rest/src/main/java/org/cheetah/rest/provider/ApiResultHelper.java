package org.cheetah.rest.provider;

import org.cheetah.commons.ExceptionMapping;
import org.cheetah.commons.PlatformException;
import org.cheetah.rest.ApiException;
import org.cheetah.rest.ApiResult;

/**
 * Created by Max on 2016/7/20.
 */
final class ApiResultHelper {

    static ApiResult doGetApiResult(Exception e) {
        ApiException metadata = getExceptionMetadata(e);
        if (null != metadata) {
            return ApiResult.error(metadata.code()).message(metadata.message()).build();
        }

        if (e instanceof PlatformException) {
            PlatformException error = (PlatformException) e;
            return ApiResult.error(error.getErrorCode()).message(error.message()).build();
        }

        return ApiResult.error(ExceptionMapping.SYSTEM.getCode()).message(ExceptionMapping.SYSTEM.getMessage()).build();
    }

    static ApiException getExceptionMetadata(Exception ex) {
        return ex.getClass().getDeclaredAnnotation(ApiException.class);
    }
}
