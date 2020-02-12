package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Author:
 * 用户信息模板
 * @Date: 2018/6/9 18:03
 */
public class UserInfoExcel {

    @Excel(name = "姓名(必填)", orderNum = "0")
    private String userName;

    @Excel(name = "邮箱(必填)", orderNum = "2")
    private String mail;

    @Excel(name = "昵称(非必填)", orderNum = "3")
    private String nickname;
    @Excel(name = "手机号(非必填)", orderNum = "4")
    private String phoneNumber;

    @Excel(name = "性别(非必填)", orderNum = "5")
    private String gender;


    public UserInfoExcel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
