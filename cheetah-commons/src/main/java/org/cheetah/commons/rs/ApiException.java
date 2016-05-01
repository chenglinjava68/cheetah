package org.cheetah.commons.rs;

/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.API_ERROR, message = "api error.")
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 8573106210247936829L;

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
