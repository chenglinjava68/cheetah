package org.cheetah.rest.exceptions;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.API_UNAVALIABLE, message = "api unavaliable.")
public class ApiUnavailableException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = 5215094125534261987L;
}
