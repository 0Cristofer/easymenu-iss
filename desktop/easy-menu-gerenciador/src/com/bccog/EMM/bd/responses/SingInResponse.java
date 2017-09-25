package com.bccog.EMM.bd.responses;

/**
 * @author Cristofer Oswald
 * @since 04/06/17
 */
public class SingInResponse {
    private String kind;
    private String idToken;
    private String email;
    private String refreshToken;
    private String expiresIn;
    private String localId;
    private boolean registered;

    public String getKind() {
        return kind;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getEmail() {
        return email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getLocalId() {
        return localId;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
