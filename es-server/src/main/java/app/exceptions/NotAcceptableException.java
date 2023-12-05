package app.exceptions;

import org.springframework.http.HttpStatus;

public class NotAcceptableException extends RuntimeException{
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_ACCEPTABLE;

    public NotAcceptableException(String message) {
        super(message);
    }

    public static HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
