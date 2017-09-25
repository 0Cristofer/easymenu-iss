package com.bccog.EMM.bd.responses.errors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public enum Message {
    @SerializedName("INVALID_EMAIL")
    INVALID_EMAL("INVALID_EMAIL"),
    @SerializedName("EMAIL_NOT_FOUND")
    EMAIL_NOT_FOUND("EMAIL_NOT_FOUND"),
    @SerializedName("INVALID_PASSWORD")
    INVALID_PASSWORD("INVALID_PASSWORD"),
    @SerializedName("MISSING_PASSWORD")
    MISSING_PASSWORD("MISSING_PASSWORD"),
    @SerializedName("USER_DISABLED")
    USER_DISABLED("USER_DISABLED"),
    @SerializedName("EMAIL_EXISTS")
    EMAIL_EXISTS("EMAIL_EXISTS"),
    @SerializedName("OPERATION_NOT_ALLOWED")
    OPERATION_NOT_ALLOWED("OPERATION_NOT_ALLOWED"),
    @SerializedName("TOO_MANY_ATTEMPTS_TRY_LATER")
    TOO_MANY_ATTEMPTS_TRY_LATER("TOO_MANY_ATTEMPTS_TRY_LATER");

    @Expose
    private String code_;

    Message(String code){
        this.code_ = code;
    }

    public String getCode() {
        return code_;
    }
}
