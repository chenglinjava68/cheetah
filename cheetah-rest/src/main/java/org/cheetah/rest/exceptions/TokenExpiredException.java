package org.cheetah.rest.exceptions;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.TOKEN_EXPIRED_ERROR, message = "token expired.")
public class TokenExpiredException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = 953877035637427812L;
}
