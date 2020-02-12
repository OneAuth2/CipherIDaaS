package com.portal.domain;


import java.io.Serializable;

public class AccountRegistration implements Serializable {
    private int isOpenRegister;
    private int isOpenMailReg;
    private int isSkipMailReg;
    private int isOpenNumReg;
    private int isSkipNumReg;

    public int getIsOpenRegister() {
        return isOpenRegister;
    }

    public void setIsOpenRegister(int isOpenRegister) {
        this.isOpenRegister = isOpenRegister;
    }

    public int getIsOpenMailReg() {
        return isOpenMailReg;
    }

    public void setIsOpenMailReg(int isOpenMailReg) {
        this.isOpenMailReg = isOpenMailReg;
    }

    public int getIsSkipMailReg() {
        return isSkipMailReg;
    }

    public void setIsSkipMailReg(int isSkipMailReg) {
        this.isSkipMailReg = isSkipMailReg;
    }

    public int getIsOpenNumReg() {
        return isOpenNumReg;
    }

    public void setIsOpenNumReg(int isOpenNumReg) {
        this.isOpenNumReg = isOpenNumReg;
    }

    public int getIsSkipNumReg() {
        return isSkipNumReg;
    }

    public void setIsSkipNumReg(int isSkipNumReg) {
        this.isSkipNumReg = isSkipNumReg;
    }

    @Override
    public String toString() {
        return "AccountRegistration{" +
                "isOpenMailReg=" + isOpenMailReg +
                ", isSkipMailReg=" + isSkipMailReg +
                ", isOpenNumReg=" + isOpenNumReg +
                ", isSkipNumReg=" + isSkipNumReg +
                '}';
    }
}
