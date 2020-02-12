package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-05-14 10:07
 */
public class DingUserDomain extends BaseDomain implements Serializable {

    /**
     * 全部
     */
    public static final Integer ALL = 0;

    /**
     * 新增
     */
    public static final Integer NEW_ADD = 1;

    /**
     * 待绑定
     */
    public static final Integer WAIT_BIND = 2;


    /**
     * 员工在当前开发者企业账号范围内的唯一标识，系统生成，固定值，不会改变
     */
    private String unionid;
    /**
     * 员工在当前企业内的唯一标识，也称staffId。可由企业在创建时指定，并代表一定含义比如工号，创建后不可修改
     */
    private String userid;
    /**
     * 是否为企业的老板，true表示是，false表示不是
     */
    private boolean isBoss;
    /**
     * 表示人员在此部门中的排序，列表是按order的倒序排列输出的，即从大到小排列输出的
     * （钉钉管理后台里面调整了顺序的话order才有值）
     */
    private long order;
    /**
     * 成员名称
     */
    private String name;
    /**
     * 国家地区码
     */
    private String stateCode;
    /**
     * 是否是部门的主管，true表示是，false表示不是
     */
    private boolean isLeader;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 员工的邮箱
     */
    private String email;
    /**
     * 表示该用户是否激活了钉钉
     */
    private boolean active;
    /**
     * 是否是企业的管理员，true表示是，false表示不是
     */
    private boolean isAdmin;
    /**
     * 手机号
     */
    private String mobile;
    /**
     *
     */
    private String openId;
    /**
     * 是否隐藏号码，true表示是，false表示不是
     */
    private boolean isHide;
    /**
     * 用户所在的部门列表
     */
    private List<Long> department;

    private String jobNumber;

    /**
     * 钉钉的用户部门，多个之间用逗号分隔，
     */
    private String departmentStr;

    /**
     * 待绑定/新增
     */
    private String status;

    /**
     * 职位
     * @return
     */
    private String position;


    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public void setBoss(boolean boss) {
        isBoss = boss;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public String getDepartment() {

        if (CollectionUtils.isEmpty(department)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Long l : department) {
            stringBuilder.append(l).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        return stringBuilder.toString();
    }

    public void setDepartment(List<Long> department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartmentStr() {
        return departmentStr;
    }

    public void setDepartmentStr(String departmentStr) {
        this.departmentStr = departmentStr;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
