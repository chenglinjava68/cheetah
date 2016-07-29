package org.cheetah.rest.exceptions;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiExceptionMetadata;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.API_PARAMS_ERROR, message = "bad params.")
public class ApiParamsException extends ApiException {
    private static final long serialVersionUID = -6045792800200735850L;
}
