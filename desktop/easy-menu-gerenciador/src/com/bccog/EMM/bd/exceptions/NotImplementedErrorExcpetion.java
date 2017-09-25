package com.bccog.EMM.bd.exceptions;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class NotImplementedErrorExcpetion extends Exception {
    public NotImplementedErrorExcpetion() {
        super();
    }

    public NotImplementedErrorExcpetion(String message) {
        super(message);
    }

    public NotImplementedErrorExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedErrorExcpetion(Throwable cause) {
        super(cause);
    }

    protected NotImplementedErrorExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
