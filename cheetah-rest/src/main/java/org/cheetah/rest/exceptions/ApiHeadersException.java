package org.cheetah.rest.exceptions;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.API_HEADERS_ERROR, message = "bad headers.")
public class ApiHeadersException extends ApiException {
    private static final long serialVersionUID = -7779891559279037433L;
}
