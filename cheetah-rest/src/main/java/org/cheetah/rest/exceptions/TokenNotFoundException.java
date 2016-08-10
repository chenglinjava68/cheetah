package org.cheetah.rest.exceptions;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.TOKEN_NOT_FOUND_ERROR, message = "token not found.")
public class TokenNotFoundException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = 1508423451948675016L;
}
