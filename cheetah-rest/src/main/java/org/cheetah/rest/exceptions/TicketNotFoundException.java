package org.cheetah.rest.exceptions;


import org.cheetah.rest.ApiConstants;
import org.cheetah.rest.ApiException;

/**
 * @author Max
 */
@ApiException(code = ApiConstants.TICKET_NOT_FOUND_ERROR, message = "ticket not found.")
public class TicketNotFoundException extends org.cheetah.rest.exceptions.ApiException {
    private static final long serialVersionUID = 763039895911862886L;
}
