package org.cheetah.reset.core;

import org.cheetah.reset.ApiConstants;
import org.cheetah.reset.ApiException;
import org.cheetah.reset.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_MISMATCH_ERROR, message = "api client mismatch.")
public class ClientMismatchException extends ApiException {
    private static final long serialVersionUID = -1219891681594115044L;
}
