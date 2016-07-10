package org.cheetah.jarrs.core;

import org.cheetah.jarrs.ApiConstants;
import org.cheetah.jarrs.ApiException;
import org.cheetah.jarrs.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_MISMATCH_ERROR, message = "api api mismatch.")
public class ClientMismatchException extends ApiException {
    private static final long serialVersionUID = -1219891681594115044L;
}
