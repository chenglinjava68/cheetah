package org.cheetah.rest.exceptions;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.API_NOT_FOUND, message = "api not found.")
public class ApiNotFoundException extends ApiException {
    private static final long serialVersionUID = -5007939522580324283L;
}
