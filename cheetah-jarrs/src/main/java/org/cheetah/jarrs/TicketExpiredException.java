package org.cheetah.jarrs;


/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.TICKET_EXPIRED_ERROR, message = "ticket expired.")
public class TicketExpiredException extends ApiException {
    private static final long serialVersionUID = -6032627373363399740L;
}
