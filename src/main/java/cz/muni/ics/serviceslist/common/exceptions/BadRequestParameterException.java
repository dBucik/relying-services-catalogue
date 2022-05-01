package cz.muni.ics.serviceslist.common.exceptions;

public class BadRequestParameterException extends Exception {
    public BadRequestParameterException() {
        super();
    }

    public BadRequestParameterException(String message) {
        super(message);
    }

    public BadRequestParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestParameterException(Throwable cause) {
        super(cause);
    }

    protected BadRequestParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
