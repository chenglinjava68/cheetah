package org.cheetah.commons.rs;


/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.TOKEN_EXPIRED_ERROR, message = "token expired.")
public class TokenExpiredException extends ApiException {
    private static final long serialVersionUID = 953877035637427812L;
}
