package org.cheetah.rest.core;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.exceptions.ApiException;
import org.cheetah.rest.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_NOT_FOUND_ERROR, message = "api not found.")
public class ClientNotFoundException extends ApiException {
    private static final long serialVersionUID = -186841720189592369L;
}
