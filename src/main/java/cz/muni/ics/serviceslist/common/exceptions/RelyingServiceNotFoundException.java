package cz.muni.ics.serviceslist.common.exceptions;

public class RelyingServiceNotFoundException extends Exception {
    public RelyingServiceNotFoundException() {
        super();
    }

    public RelyingServiceNotFoundException(String message) {
        super(message);
    }

    public RelyingServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RelyingServiceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RelyingServiceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
