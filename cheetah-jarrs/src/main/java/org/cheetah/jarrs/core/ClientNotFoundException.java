package org.cheetah.jarrs.core;


import org.cheetah.jarrs.ApiConstants;
import org.cheetah.jarrs.ApiException;
import org.cheetah.jarrs.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_NOT_FOUND_ERROR, message = "api not found.")
public class ClientNotFoundException extends ApiException {
    private static final long serialVersionUID = -186841720189592369L;
}
