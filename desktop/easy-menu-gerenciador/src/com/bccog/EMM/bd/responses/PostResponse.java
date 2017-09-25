package com.bccog.EMM.bd.responses;

import com.google.gson.annotations.SerializedName;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class PostResponse {
    @SerializedName("name")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
