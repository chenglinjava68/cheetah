package org.cheetah.rest.provider;

import org.cheetah.common.ExceptionMapping;
import org.cheetah.common.PlatformException;
import org.cheetah.rest.ApiExceptionMetadata;
import org.cheetah.rest.ApiResult;

/**
 * Created by Max on 2016/7/20.
 */
final class ApiResultHelper {

    static ApiResult doGetApiResult(Exception e) {
        ApiExceptionMetadata metadata = getExceptionMetadata(e);
        if (null != metadata) {
            return ApiResult.error(metadata.code()).message(metadata.message()).build();
        }

        if (e instanceof PlatformException) {
            PlatformException error = (PlatformException) e;
            return ApiResult.error(error.getErrorCode()).message(error.message()).build();
        }

        return ApiResult.error(ExceptionMapping.SYSTEM.getCode()).message(ExceptionMapping.SYSTEM.getMessage()).build();
    }

    static ApiExceptionMetadata getExceptionMetadata(Exception ex) {
        return ex.getClass().getDeclaredAnnotation(ApiExceptionMetadata.class);
    }
}
