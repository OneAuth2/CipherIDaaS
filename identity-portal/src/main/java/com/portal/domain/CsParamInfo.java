package com.portal.domain;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/9/27
 *
 * @author liuying
 * @since 2019/9/27 15:42
 */
public class CsParamInfo implements Serializable {

    private String notFoundUrl;
    private String notFoundMsg;
    private String registerTitle;
    private String loginWindowType;
    private String loginWindowTitle;
    private String LoginedWindowType;
    private String loginedWindowTitle;
    private String requestId;
    private String requestValue;




    public String getNotFoundUrl() {
        return notFoundUrl;
    }

    public void setNotFoundUrl(String notFoundUrl) {
        this.notFoundUrl = notFoundUrl;
    }

    public String getNotFoundMsg() {
        return notFoundMsg;
    }

    public void setNotFoundMsg(String notFoundMsg) {
        this.notFoundMsg = notFoundMsg;
    }

    public String getRegisterTitle() {
        return registerTitle;
    }

    public void setRegisterTitle(String registerTitle) {
        this.registerTitle = registerTitle;
    }

    public String getLoginWindowType() {
        return loginWindowType;
    }

    public void setLoginWindowType(String loginWindowType) {
        this.loginWindowType = loginWindowType;
    }

    public String getLoginWindowTitle() {
        return loginWindowTitle;
    }

    public void setLoginWindowTitle(String loginWindowTitle) {
        this.loginWindowTitle = loginWindowTitle;
    }

    public String getLoginedWindowType() {
        return LoginedWindowType;
    }

    public void setLoginedWindowType(String loginedWindowType) {
        LoginedWindowType = loginedWindowType;
    }

    public String getLoginedWindowTitle() {
        return loginedWindowTitle;
    }

    public void setLoginedWindowTitle(String loginedWindowTitle) {
        this.loginedWindowTitle = loginedWindowTitle;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(String requestValue) {
        this.requestValue = requestValue;
    }
}
