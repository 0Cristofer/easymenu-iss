package com.bccog.EMM.gerenciadores.exceptions;

/**
 * @author Cristofer Oswald
 * @since 30/05/17
 */
public class AutentificationException extends Exception {
    public AutentificationException() {
        super();
    }

    public AutentificationException(String message) {
        super(message);
    }

    public AutentificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutentificationException(Throwable cause) {
        super(cause);
    }

    protected AutentificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
