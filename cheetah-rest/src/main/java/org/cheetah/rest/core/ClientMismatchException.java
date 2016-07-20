package org.cheetah.rest.core;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.exceptions.ApiException;
import org.cheetah.rest.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_MISMATCH_ERROR, message = "api mismatch.")
public class ClientMismatchException extends ApiException {
    private static final long serialVersionUID = -1219891681594115044L;
}
