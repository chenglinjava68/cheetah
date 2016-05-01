package org.cheetah.commons.rs;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.API_NOT_FOUND, message = "api not found.")
public class ApiNotFoundException extends ApiException {
    private static final long serialVersionUID = -5007939522580324283L;
}
