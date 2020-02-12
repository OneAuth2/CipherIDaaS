package com.portal.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class RequestTotp implements Serializable{
    private String username;

    private String password;

    private int totp;

    private long time;

    private int status;

    public RequestTotp() {
    }

    public RequestTotp(String username, int totp, long time, int status) {
        this.username = username;
        this.totp = totp;
        this.time = time;
        this.status = status;
    }

    public RequestTotp(String username, String password, int totp, long time, int status) {
        this.username = username;
        this.password = password;
        this.totp = totp;
        this.time = time;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotp() {
        return totp;
    }

    public void setTotp(int totp) {
        this.totp = totp;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RequestTotp{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", totp=" + totp +
                ", time=" + time +
                ", status=" + status +
                '}';
    }
}
