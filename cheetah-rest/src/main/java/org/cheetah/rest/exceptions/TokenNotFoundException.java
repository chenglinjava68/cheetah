package org.cheetah.rest.exceptions;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.TOKEN_NOT_FOUND_ERROR, message = "token not found.")
public class TokenNotFoundException extends ApiException {
    private static final long serialVersionUID = 1508423451948675016L;
}
