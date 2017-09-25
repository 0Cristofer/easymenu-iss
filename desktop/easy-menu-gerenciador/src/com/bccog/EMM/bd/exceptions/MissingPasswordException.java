package com.bccog.EMM.bd.exceptions;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class MissingPasswordException extends Exception {
    public MissingPasswordException() {
        super();
    }

    public MissingPasswordException(String message) {
        super(message);
    }

    public MissingPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingPasswordException(Throwable cause) {
        super(cause);
    }

    protected MissingPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
