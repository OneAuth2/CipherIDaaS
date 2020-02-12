package com.portal.model;

public class User {
    String username;
    String password;
    String secret;
    int totpCount;

    public User() {
    }

    public User(String username, String password, String secret, int totpCount) {
        this.username = username;
        this.password = password;
        this.secret = secret;
        this.totpCount = totpCount;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getTotpCount() {
        return totpCount;
    }

    public void setTotpCount(int totpCount) {
        this.totpCount = totpCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", secret='" + secret + '\'' +
                ", totpCount=" + totpCount +
                '}';
    }
}
