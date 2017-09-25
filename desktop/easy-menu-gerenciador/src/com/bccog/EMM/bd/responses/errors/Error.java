package com.bccog.EMM.bd.responses.errors;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class Error {
    private String domain;
    private String reason;
    private Message message;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
