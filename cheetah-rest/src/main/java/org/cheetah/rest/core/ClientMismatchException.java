package org.cheetah.rest.core;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.CLIENT_MISMATCH_ERROR, message = "api mismatch.")
public class ClientMismatchException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = -1219891681594115044L;
}
