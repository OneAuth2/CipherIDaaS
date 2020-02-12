package com.portal.domain;


import java.io.Serializable;

/**
 * 用户登录的实体类
 * create by shizhao at 2019/5/15
 *
 * @author shizhao
 * @since  2019/5/15
 * */
public class LoginInfo implements Serializable {

    //是否为首次登录
    //0-首次，1-非首次
    private int firstLogin;

    //是否需要修改密码
    //0-需要，1-不需要
    private int updatePwd;

    //是否进行二次认证
    //0-需要，1-不需要
    private int secondLogin;

    //是否采集信息手机号
    //0-需要，1-不需要
    private int infoCollection;

    //是否采集信息邮箱
    //0-需要，1-不需要
    private int infoCollectionMail;

    //用户的唯一标识
    private String userId;


    //二次认证方式
    //0-并行，1-串行
    private int switches;


    public int getSwitches() {
        return switches;
    }

    public void setSwitches(int switches) {
        this.switches = switches;
    }

    public LoginInfo() {
    }

    public LoginInfo(int firstLogin, int updatePwd, int secondLogin, int infoCollection, int switches,String userId) {
        this.firstLogin = firstLogin;
        this.updatePwd = updatePwd;
        this.secondLogin = secondLogin;
        this.infoCollection = infoCollection;
        this.switches=switches;
        this.userId = userId;
    }

    public LoginInfo(int firstLogin, int updatePwd, int secondLogin, int infoCollection, int infoCollectionMail,int switches, String userId) {
        this(firstLogin, updatePwd, secondLogin, infoCollection,switches, userId);
        this.infoCollectionMail = infoCollectionMail;
    }

    public int getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(int firstLogin) {
        this.firstLogin = firstLogin;
    }

    public int getUpdatePwd() {
        return updatePwd;
    }

    public void setUpdatePwd(int updatePwd) {
        this.updatePwd = updatePwd;
    }

    public int getSecondLogin() {
        return secondLogin;
    }

    public void setSecondLogin(int secondLogin) {
        this.secondLogin = secondLogin;
    }

    public int getInfoCollection() {
        return infoCollection;
    }

    public void setInfoCollection(int infoCollection) {
        this.infoCollection = infoCollection;
    }


    public boolean needSecondAuth(){
        return secondLogin == 1?false:true;
    }

    //判断是否并行
    public boolean isParallel(){
        return switches == 0?true:false;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "firstLogin=" + firstLogin +
                ", updatePwd=" + updatePwd +
                ", secondLogin=" + secondLogin +
                ", infoCollection=" + infoCollection +
                ", infoCollectionMail=" + infoCollectionMail +
                ", userId='" + userId + '\'' +
                '}';
    }

    public int getInfoCollectionMail() {
        return infoCollectionMail;
    }

    public void setInfoCollectionMail(int infoCollectionMail) {
        this.infoCollectionMail = infoCollectionMail;
    }
}
