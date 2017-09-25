package com.bccog.EMM.bd.responses.errors;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class MainError {
    private Error[] errors;
    private String code;
    private Message message;

    public Error[] getErrors() {
        return errors;
    }

    public void setErrors(Error[] errors) {
        this.errors = errors;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
