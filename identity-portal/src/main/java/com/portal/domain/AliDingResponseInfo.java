package com.portal.domain;


import java.io.Serializable;

public class AliDingResponseInfo implements Serializable {

    private User_info user_info;
    private String errmsg;
    private int errcode;
    public void setUser_info(User_info user_info) {
        this.user_info = user_info;
    }
    public User_info getUser_info() {
        return user_info;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    public String getErrmsg() {
        return errmsg;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }
    public int getErrcode() {
        return errcode;
    }

    public class User_info {

        private String dingId;
        private String nick;
        private String unionid;
        private String openid;
        public void setDingId(String dingId) {
            this.dingId = dingId;
        }
        public String getDingId() {
            return dingId;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }
        public String getNick() {
            return nick;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }
        public String getUnionid() {
            return unionid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
        public String getOpenid() {
            return openid;
        }

    }

    @Override
    public String toString() {
        return "AliDingResponseInfo{" +
                "user_info=" + user_info +
                ", errmsg='" + errmsg + '\'' +
                ", errcode=" + errcode +
                '}';
    }
}
