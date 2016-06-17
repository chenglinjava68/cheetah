package org.cheetah.rs.core;


import org.cheetah.rs.ApiConstants;
import org.cheetah.rs.ApiException;
import org.cheetah.rs.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_NOT_FOUND_ERROR, message = "client not found.")
public class ClientNotFoundException extends ApiException {
    private static final long serialVersionUID = -186841720189592369L;
}
