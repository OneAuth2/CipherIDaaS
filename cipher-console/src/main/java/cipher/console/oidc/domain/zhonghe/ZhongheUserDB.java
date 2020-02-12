package cipher.console.oidc.domain.zhonghe;

import java.io.Serializable;
import java.util.Date;

public class ZhongheUserDB implements Serializable {

    private String staffName;
    private int department;
    private int pk;
    private int post;
    private String staffStatus;
    private String userName;
    private String email;
    private String posts;
    private String departments;
    private Date createTime;



    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ZhongheUserDB{" +
                "staffName='" + staffName + '\'' +
                ", department=" + department +
                ", pk=" + pk +
                ", post=" + post +
                ", staffStatus='" + staffStatus + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", posts='" + posts + '\'' +
                ", departments='" + departments + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
