package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

/**
 * modify by 田扛
 * modify time 2019年3月11日16:10:10
 * 密码策略
 */
public class PasswordSettingDomain extends BaseDomain {
    /**
     * 编号
     *
     * */
    int id;

    /**
     * 密码最小长度
     *
     * */
    int length;

    /**
     * 初始密码
     *
     * */
    String init;

    /**
     * password type 密码类型1是简单，2是中等，3是复杂
     * @return
     */
    private String passwordType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(String passwordType) {
        this.passwordType = passwordType;
    }

    @Override
    public String toString() {
        return "PasswordSettingDomain{" +
                "id=" + id +
                ", length=" + length +
                ", init='" + init + '\'' +
                ", passwordType='" + passwordType + '\'' +
                '}';
    }
}
