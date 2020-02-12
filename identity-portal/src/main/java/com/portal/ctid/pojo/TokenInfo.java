package com.portal.ctid.pojo;

public class TokenInfo{

    private String authType;

    private String certToken;

    private String createdAt;

    private String expireAt;

    private long expireTimeMs;

    private String qrcodeContent;

    private long timestamp;

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getCertToken() {
        return certToken;
    }

    public void setCertToken(String certToken) {
        this.certToken = certToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public long getExpireTimeMs() {
        return expireTimeMs;
    }

    public void setExpireTimeMs(long expireTimeMs) {
        this.expireTimeMs = expireTimeMs;
    }

    public String getQrcodeContent() {
        return qrcodeContent;
    }

    public void setQrcodeContent(String qrcodeContent) {
        this.qrcodeContent = qrcodeContent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "authType='" + authType + '\'' +
                ", certToken='" + certToken + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", expireAt='" + expireAt + '\'' +
                ", expireTimeMs=" + expireTimeMs +
                ", qrcodeContent='" + qrcodeContent + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
