package com.portal.model;

import java.io.Serializable;

public class DabbyUserInfoModel implements Serializable {

    private String cert_token;

    private int cert_mode;

    private String full_name;

    private String id_num;

    private Integer cert_res;

    private String cert_str;

    private String sig;


    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getCert_token() {
        return cert_token;
    }

    public void setCert_token(String cert_token) {
        this.cert_token = cert_token;
    }

    public int getCert_mode() {
        return cert_mode;
    }

    public void setCert_mode(int cert_mode) {
        this.cert_mode = cert_mode;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public Integer getCert_res() {
        return cert_res;
    }

    public void setCert_res(Integer cert_res) {
        this.cert_res = cert_res;
    }

    public String getCert_str() {
        return cert_str;
    }

    public void setCert_str(String cert_str) {
        this.cert_str = cert_str;
    }

    @Override
    public String toString() {
        return "DabbyUserInfoModel{" +
                "cert_token='" + cert_token + '\'' +
                ", cert_mode=" + cert_mode +
                ", full_name='" + full_name + '\'' +
                ", id_num='" + id_num + '\'' +
                ", cert_res='" + cert_res + '\'' +
                ", cert_str='" + cert_str + '\'' +
                ", sig='" + sig + '\'' +
                '}';
    }
}
