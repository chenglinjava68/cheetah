package org.cheetah.rest.core;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.CLIENT_NOT_FOUND_ERROR, message = "api not found.")
public class ClientNotFoundException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = -186841720189592369L;
}
