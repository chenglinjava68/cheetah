package org.cheetah.rest.exceptions;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.TOKEN_MISSING_ERROR, message = "token missing.")
public class TokenMissingException extends ApiParamsException {
    private static final long serialVersionUID = 5222697643470778761L;
}
