package org.cheetah.rest.exceptions;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.TICKET_EXPIRED_ERROR, message = "ticket expired.")
public class TicketExpiredException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = -6032627373363399740L;
}
