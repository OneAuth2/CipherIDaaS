package com.portal.domain;


import java.io.Serializable;

public class AccountBinding implements Serializable {
    private int bindingAppSf;
    private int autoBindingSf;
    private int bindingAppDd;
    private int autoBindingDd;
    private int bindingAppDb;
    private int autoBindingDb;
    private int isOpenMailBind;
    private int isSkipMailBind;
    private int isOpenNumBind;
    private int isSkipNumBind;
    private int bindingAppWx;
    private int autoBindingWx;

    public int getBindingAppWx() {
        return bindingAppWx;
    }

    public void setBindingAppWx(int bindingAppWx) {
        this.bindingAppWx = bindingAppWx;
    }

    public int getAutoBindingWx() {
        return autoBindingWx;
    }

    public void setAutoBindingWx(int autoBindingWx) {
        this.autoBindingWx = autoBindingWx;
    }

    public int getBindingAppSf() {
        return bindingAppSf;
    }

    public void setBindingAppSf(int bindingAppSf) {
        this.bindingAppSf = bindingAppSf;
    }

    public int getAutoBindingSf() {
        return autoBindingSf;
    }

    public void setAutoBindingSf(int autoBindingSf) {
        this.autoBindingSf = autoBindingSf;
    }

    public int getBindingAppDd() {
        return bindingAppDd;
    }

    public void setBindingAppDd(int bindingAppDd) {
        this.bindingAppDd = bindingAppDd;
    }

    public int getAutoBindingDd() {
        return autoBindingDd;
    }

    public void setAutoBindingDd(int autoBindingDd) {
        this.autoBindingDd = autoBindingDd;
    }

    public int getBindingAppDb() {
        return bindingAppDb;
    }

    public void setBindingAppDb(int bindingAppDb) {
        this.bindingAppDb = bindingAppDb;
    }

    public int getAutoBindingDb() {
        return autoBindingDb;
    }

    public void setAutoBindingDb(int autoBindingDb) {
        this.autoBindingDb = autoBindingDb;
    }

    public int getIsOpenMailBind() {
        return isOpenMailBind;
    }

    public void setIsOpenMailBind(int isOpenMailBind) {
        this.isOpenMailBind = isOpenMailBind;
    }

    public int getIsSkipMailBind() {
        return isSkipMailBind;
    }

    public void setIsSkipMailBind(int isSkipMailBind) {
        this.isSkipMailBind = isSkipMailBind;
    }

    public int getIsOpenNumBind() {
        return isOpenNumBind;
    }

    public void setIsOpenNumBind(int isOpenNumBind) {
        this.isOpenNumBind = isOpenNumBind;
    }

    public int getIsSkipNumBind() {
        return isSkipNumBind;
    }

    public void setIsSkipNumBind(int isSkipNumBind) {
        this.isSkipNumBind = isSkipNumBind;
    }

    @Override
    public String toString() {
        return "AccountBinding{" +
                "bindingAppSf=" + bindingAppSf +
                ", autoBindingSf=" + autoBindingSf +
                ", bindingAppDd=" + bindingAppDd +
                ", autoBindingDd=" + autoBindingDd +
                ", bindingAppDb=" + bindingAppDb +
                ", autoBindingDb=" + autoBindingDb +
                ", isOpenMailBind=" + isOpenMailBind +
                ", isSkipMailBind=" + isSkipMailBind +
                ", isOpenNumBind=" + isOpenNumBind +
                ", isSkipNumBind=" + isSkipNumBind +
                '}';
    }
}
