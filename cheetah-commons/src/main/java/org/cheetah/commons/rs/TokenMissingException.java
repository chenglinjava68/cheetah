package org.cheetah.commons.rs;


/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.TOKEN_MISSING_ERROR, message = "token missing.")
public class TokenMissingException extends ApiParamsException {
    private static final long serialVersionUID = 5222697643470778761L;
}
