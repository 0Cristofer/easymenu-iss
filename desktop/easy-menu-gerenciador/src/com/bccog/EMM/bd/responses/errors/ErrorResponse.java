package com.bccog.EMM.bd.responses.errors;


/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class ErrorResponse {
    private MainError error;

    public MainError getError() {
        return error;
    }

    public void setError(MainError error) {
        this.error = error;
    }
}
