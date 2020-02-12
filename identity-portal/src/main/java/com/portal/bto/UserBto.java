package com.portal.bto;

import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/12/9
 *
 * @author liuying
 * @since 2019/12/9 18:12
 */
public class UserBto implements Serializable {
    private String id;
    private String uname;
    private String Email;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
