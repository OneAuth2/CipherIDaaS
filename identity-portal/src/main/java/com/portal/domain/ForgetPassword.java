package com.portal.domain;

import java.io.Serializable;

public class ForgetPassword implements Serializable {
    private int isOpenForgetPwd;
    private int isOpenMailForget;
    private int isOpenNumForget;
   // private int twoAuthForgetSf ;
    private int twoAuthForgetDd;
    private int twoAuthForgetDb;
    private int twoAuthForgetWx;


  /*  public int getTwoAuthForgetSf() {
        this.twoAuthForgetSf=1;
        return this.twoAuthForgetSf;
    }

    public void setTwoAuthForgetSf(int twoAuthForgetSf) {
        this.twoAuthForgetSf = twoAuthForgetSf;
    }*/

    public int getTwoAuthForgetWx() {
        return twoAuthForgetWx;
    }

    public void setTwoAuthForgetWx(int twoAuthForgetWx) {
        this.twoAuthForgetWx = twoAuthForgetWx;
    }

    public int getIsOpenForgetPwd() {
        return isOpenForgetPwd;
    }

    public void setIsOpenForgetPwd(int isOpenForgetPwd) {
        this.isOpenForgetPwd = isOpenForgetPwd;
    }

    public int getIsOpenMailForget() {
        return isOpenMailForget;
    }

    public void setIsOpenMailForget(int isOpenMailForget) {
        this.isOpenMailForget = isOpenMailForget;
    }

    public int getIsOpenNumForget() {
        return isOpenNumForget;
    }

    public void setIsOpenNumForget(int isOpenNumForget) {
        this.isOpenNumForget = isOpenNumForget;
    }


    public int getTwoAuthForgetDd() {
        return twoAuthForgetDd;
    }

    public void setTwoAuthForgetDd(int twoAuthForgetDd) {
        this.twoAuthForgetDd = twoAuthForgetDd;
    }

    public int getTwoAuthForgetDb() {
        return twoAuthForgetDb;
    }

    public void setTwoAuthForgetDb(int twoAuthForgetDb) {
        this.twoAuthForgetDb = twoAuthForgetDb;
    }

    public ForgetPassword(int isOpenForgetPwd, int isOpenMailForget, int isOpenNumForget, int twoAuthForgetSf, int twoAuthForgetDd, int twoAuthForgetDb, int twoAuthForgetWx) {
        this.isOpenForgetPwd = isOpenForgetPwd;
        this.isOpenMailForget = isOpenMailForget;
        this.isOpenNumForget = isOpenNumForget;
    //    this.twoAuthForgetSf = twoAuthForgetSf;
        this.twoAuthForgetDd = twoAuthForgetDd;
        this.twoAuthForgetDb = twoAuthForgetDb;
        this.twoAuthForgetWx = twoAuthForgetWx;
    }
}



