package cipher.console.oidc.domain.web;

/**
 * @Author: TK
 * 腾讯企业邮下发的规则
 * @Date: 2018/12/6 17:29
 */
public class TencentSubRule {
    private String  userName;//用户名 必填字段
    private String department;//部门 必填字段
    private String position;//职位 非必填字段
    private String phone; //手机 非必填字段
    private String number; //编号 非必填字段
    private String sex ;//性别 非必填字段

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
        return "TencentSubRule{" +
                "userName='" + userName + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", number='" + number + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
