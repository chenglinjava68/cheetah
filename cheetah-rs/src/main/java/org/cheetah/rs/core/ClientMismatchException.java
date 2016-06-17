package org.cheetah.rs.core;

import org.cheetah.rs.ApiConstants;
import org.cheetah.rs.ApiException;
import org.cheetah.rs.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_MISMATCH_ERROR, message = "api client mismatch.")
public class ClientMismatchException extends ApiException {
    private static final long serialVersionUID = -1219891681594115044L;
}
