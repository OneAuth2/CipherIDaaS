package com.portal.domain;

import java.io.Serializable;

/**
 * @Author: TK
 * @Date: 2019/5/7 19:23
 */
public class SecondLoginInfo implements Serializable {
    private int twoAuthSf;//是否扫码
    private int twoAuthDd;//钉钉
    private int twoAuthDb;//大白
    private int twoAuthNum;//电话
    private int twoAuthMail;//邮箱
    private int twoAuthDt;//otp
    private int twoAuthDingPush;//钉钉push 认证
    private int switches;//串行和并行的开关
    private int otpBindKey; //otp是否重置 1:关闭 0：开启
    private int twoAuthWx;//微信


    public int getTwoAuthWx() {
        return twoAuthWx;
    }

    public void setTwoAuthWx(int twoAuthWx) {
        this.twoAuthWx = twoAuthWx;
    }

    public int getOtpBindKey() {
        return otpBindKey;
    }

    public void setOtpBindKey(int otpBindKey) {
        this.otpBindKey = otpBindKey;
    }

    public int getSwitches() {
        return switches;
    }

    public void setSwitches(int switches) {
        this.switches = switches;
    }

    public int getTwoAuthSf() {
        return twoAuthSf =1 ;
    }

    public void setTwoAuthSf(int twoAuthSf) {
        this.twoAuthSf = twoAuthSf;
    }

    public int getTwoAuthDd() {
        return twoAuthDd;
    }

    public void setTwoAuthDd(int twoAuthDd) {
        this.twoAuthDd = twoAuthDd;
    }

    public int getTwoAuthDb() {
        return twoAuthDb;
    }

    public void setTwoAuthDb(int twoAuthDb) {
        this.twoAuthDb = twoAuthDb;
    }

    public int getTwoAuthNum() {
        return twoAuthNum;
    }

    public void setTwoAuthNum(int twoAuthNum) {
        this.twoAuthNum = twoAuthNum;
    }

    public int getTwoAuthMail() {
        return twoAuthMail;
    }

    public void setTwoAuthMail(int twoAuthMail) {
        this.twoAuthMail = twoAuthMail;
    }

    public int getTwoAuthDt() {
        return twoAuthDt;
    }

    public void setTwoAuthDt(int twoAuthDt) {
        this.twoAuthDt = twoAuthDt;
    }

    public int getTwoAuthDingPush() {
        return twoAuthDingPush;
    }

    public void setTwoAuthDingPush(int twoAuthDingPush) {
        this.twoAuthDingPush = twoAuthDingPush;
    }


    public SecondLoginInfo() {
    }

    @Override
    public String toString() {
        return "SecondLoginInfo{" +
                "twoAuthSf=" + twoAuthSf +
                ", twoAuthDd=" + twoAuthDd +
                ", twoAuthDb=" + twoAuthDb +
                ", twoAuthNum=" + twoAuthNum +
                ", twoAuthMail=" + twoAuthMail +
                ", twoAuthDt=" + twoAuthDt +
                ", twoAuthDingPush=" + twoAuthDingPush +
                ", switches=" + switches +
                ", otpBindKey=" + otpBindKey +
                '}';
    }
}
