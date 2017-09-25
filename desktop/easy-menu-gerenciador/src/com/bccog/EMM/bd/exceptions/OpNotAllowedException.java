package com.bccog.EMM.bd.exceptions;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class OpNotAllowedException extends Exception {
    public OpNotAllowedException() {
        super();
    }

    public OpNotAllowedException(String message) {
        super(message);
    }

    public OpNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpNotAllowedException(Throwable cause) {
        super(cause);
    }

    protected OpNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
