package com.portal.domain;

public class BaseReq {

    private String messageCode;

    private Object content="";

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public BaseReq() {
    }

    public BaseReq(String messageCode, Object content) {
        this.messageCode = messageCode;
        this.content = content;
    }
}
