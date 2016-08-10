package org.cheetah.rest.exceptions;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.API_HEADERS_ERROR, message = "bad headers.")
public class ApiHeadersException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = -7779891559279037433L;
}
