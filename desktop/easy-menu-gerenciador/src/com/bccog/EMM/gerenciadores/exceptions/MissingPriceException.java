package com.bccog.EMM.gerenciadores.exceptions;

/**
 * @author Cristofer Oswald
 * @since 06/06/17
 */
public class MissingPriceException extends Exception {
    public MissingPriceException() {
        super();
    }

    public MissingPriceException(String message) {
        super(message);
    }

    public MissingPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingPriceException(Throwable cause) {
        super(cause);
    }

    protected MissingPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
