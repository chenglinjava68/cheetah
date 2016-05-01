package org.cheetah.commons.rs.core;


import org.cheetah.commons.rs.ApiConstants;
import org.cheetah.commons.rs.ApiException;
import org.cheetah.commons.rs.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.CLIENT_NOT_FOUND_ERROR, message = "client not found.")
public class ClientNotFoundException extends ApiException {
    private static final long serialVersionUID = -186841720189592369L;
}
