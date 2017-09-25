package com.bccog.EMM.bd.exceptions;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class TooManyAttempsException extends Exception {
    public TooManyAttempsException() {
        super();
    }

    public TooManyAttempsException(String message) {
        super(message);
    }

    public TooManyAttempsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyAttempsException(Throwable cause) {
        super(cause);
    }

    protected TooManyAttempsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
