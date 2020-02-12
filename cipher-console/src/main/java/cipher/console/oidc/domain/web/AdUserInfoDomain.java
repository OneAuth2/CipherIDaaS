package cipher.console.oidc.domain.web;

import org.springframework.ldap.odm.annotations.Entry;

import java.util.Date;

/**
 * @Author: zt
 * @Date: 2018/6/8 16:33
 */
@Entry(objectClasses = { "person", "top" }, base="dc=cipherchina,dc=com")
public class AdUserInfoDomain {

    private Integer id;

    private String userName;

    private String nickName;


    /**
     * 用户账号状态
     */
    private Integer userAccountControl;

    private String nodeInfo;

    private String mail;

    private String uniqueName;



    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }



    public Integer getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(Integer userAccountControl) {
        this.userAccountControl = userAccountControl;
    }


    public String getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "AdUserInfoDomain{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", nodeInfo='" + nodeInfo + '\'' +
                ", mail='" + mail + '\'' +
                ", uniqueName='" + uniqueName + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
