package org.cheetah.reset.core;


import org.cheetah.reset.ApiConstants;
import org.cheetah.reset.ApiException;
import org.cheetah.reset.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_NOT_FOUND_ERROR, message = "client not found.")
public class ClientNotFoundException extends ApiException {
    private static final long serialVersionUID = -186841720189592369L;
}
