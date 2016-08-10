package org.cheetah.rest.exceptions;

import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * Created by Max on 2016/1/22.
 */
@ApiException(code = ApiConstants.API_ILLEGAL_ADDRESS_ERROR, message = "illegal address.")
public class IllegalVisitorAddressException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = 2124385967654845154L;
}
