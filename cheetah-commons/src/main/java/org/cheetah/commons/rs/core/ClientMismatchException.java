package org.cheetah.commons.rs.core;

import org.cheetah.commons.rs.ApiConstants;
import org.cheetah.commons.rs.ApiException;
import org.cheetah.commons.rs.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_MISMATCH_ERROR, message = "api client mismatch.")
public class ClientMismatchException extends ApiException {
    private static final long serialVersionUID = -1219891681594115044L;
}
